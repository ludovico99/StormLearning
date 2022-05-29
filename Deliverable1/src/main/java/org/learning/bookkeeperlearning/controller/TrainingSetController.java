package org.learning.bookkeeperlearning.controller;

import org.learning.bookkeeperlearning.entity.DataSetEntity;
import org.learning.bookkeeperlearning.entity.JiraTicketsEntity;
import org.learning.bookkeeperlearning.entity.ReleaseEntity;
import org.learning.bookkeeperlearning.utilityclasses.ArffFiles;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class TrainingSetController extends DataSetController {

    @Override
    public void writeArffFile(DataSetEntity dataSet) throws IOException {
        ArffFiles.addLinesToDataSet(dataSet,true);
        ArffFiles.getWriterTraining().close();
    }

    @Override
    protected void computeAvsVersionsForAllTickets(List<ReleaseEntity> releases, List<JiraTicketsEntity> jiraTicketsEntityList) {

            int iv = -1;
            int fv;
            int ov;

            List<ReleaseEntity> computedAvs;

            for (JiraTicketsEntity ticket : jiraTicketsEntityList) {
                //Calcolo prima l'AVs per ticketId e poi trovo le classi
                //modificate da commit con quel ticketID

                if (ticket.getFv() != null) {
                    fv = releases.indexOf(ticket.getFv());
                    ov = releases.indexOf(ticket.getOv());
                    if (fv==0) {
                        ticket.setAvs(new ArrayList<>());
                        continue;
                    }
                    if (ticket.getAvsJira().isEmpty()) {
                        if (fv > ov)
                            iv = (int) (fv - (fv - ov) * ticket.getIncrementalP().get(fv-1));
                        else iv = (int) (fv - ticket.getIncrementalFvIv().get(fv-1));
                    } else {
                        List<ReleaseEntity> affectedVersions = ticket.getAvsJira();
                        List<Integer> indexes = new ArrayList<>();
                        for (ReleaseEntity release : affectedVersions) {//trovare l'earliest AVs
                            for (int j = 0; j < releases.size(); j++) {
                                if (releases.get(j).getVersion().contains(release.getVersion())) {
                                    indexes.add(j);
                                }
                            }
                        }
                        Collections.sort(indexes);
                        if (!indexes.isEmpty()) { //almeno una delle versioni è in AVs
                            iv = indexes.get(0);
                            if (iv >= fv || iv >= ov) //iv trovato ma inconsistente
                            {
                                if (fv <= ov)
                                    iv = (fv - ticket.getIncrementalFvIv().get(fv-1).intValue()); //<--ov inconsistente
                                else iv = (int) (fv - (fv - ov) * ticket.getIncrementalP().get(fv-1));
                            }
                        } else { //<-- non ho trovato la earliest released Av
                            if (fv <= ov) iv = fv - ticket.getIncrementalFvIv().get(fv-1).intValue(); //<--ov inconsistente
                            else iv = (int) (fv - (fv - ov) * ticket.getIncrementalP().get(fv-1));
                        }

                    }
                    if (iv < 0) iv = 0; // ho sottratto mean fv-iv ed è maggiore di fv
                    computedAvs= new ArrayList<>(releases.subList(iv, fv));
                    ticket.setIv(releases.get(iv));
                    ticket.setAvs(computedAvs);
                }
            }
    }
}
