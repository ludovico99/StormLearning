package org.learning.stormlearning.controller;

import org.learning.stormlearning.entity.DataSetEntity;
import org.learning.stormlearning.entity.JiraTicketsEntity;
import org.learning.stormlearning.entity.ReleaseEntity;
import org.learning.stormlearning.utilityclasses.ArffFiles;

import java.io.IOException;

import java.util.List;

public class TrainingSetController extends DataSetController {

    @Override
    public void writeArffFile(DataSetEntity dataSet) throws IOException {
        ArffFiles.addLinesToDataSet(dataSet,true);
        ArffFiles.getWriterTraining().close();
    }

    @Override
    public void computeAvsVersionsForAllTickets(List<ReleaseEntity> releases, List<JiraTicketsEntity> jiraTicketsEntityList) {
        for (JiraTicketsEntity ticket : jiraTicketsEntityList) {
            if (ticket.getFv() != null) {
                int fv = releases.indexOf(ticket.getFv());
                if (fv < 1) continue;
                computeIv(ticket,releases,fv - 1);

            }
        }
    }
}
