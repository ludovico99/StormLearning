package org.learning.stormlearning.controller;

import org.learning.stormlearning.entity.LearningModelEntity;
import org.learning.stormlearning.utility.BalancingEnum;
import weka.classifiers.AbstractClassifier;
import weka.classifiers.Evaluation;
import weka.classifiers.meta.FilteredClassifier;
import weka.core.Instances;
import weka.filters.supervised.instance.Resample;
import weka.filters.supervised.instance.SMOTE;
import weka.filters.supervised.instance.SpreadSubsample;

import java.util.logging.Level;
import java.util.logging.Logger;


public class BalancingDecorator extends Decorator {

    private final BalancingEnum value;

    private final Logger logger = Logger.getLogger("Balancing log");

    public BalancingDecorator(Validation val, BalancingEnum value) {
        super(val.validationEntity.getDataSetName() + " with balancing techniques",val);
        this.value = value;

    }

    public void initSmote(FilteredClassifier fc, Instances training) throws Exception {
        SMOTE smote = new SMOTE();
        smote.setInputFormat(training);
        fc.setFilter(smote);
    }

    public void initUnderSampling(FilteredClassifier fc) throws Exception {
        SpreadSubsample spreadSubsample = new SpreadSubsample();
        String[] opts = new String[]{"-M", "1.0"};
        spreadSubsample.setOptions(opts);
        fc.setFilter(spreadSubsample);
    }

    public void initOverSampling(FilteredClassifier fc,Instances training) throws Exception {
        int majority;
        int minority;
        Resample resample = new Resample();
        int numAttr = training.numAttributes();


        majority = training.attributeStats(numAttr - 1).nominalCounts[0];
        minority = training.attributeStats(numAttr - 1).nominalCounts[1];

        double sampleSizePercent;

        if(majority < minority)  sampleSizePercent  = 100 * (minority - majority) / (double) majority;
        else sampleSizePercent  = 100 * (majority - minority) / (double) minority;

        String[] opts = new String[]{"-B", "1.0", "-Z", String.valueOf(sampleSizePercent)};
        resample.setOptions(opts);
        fc.setFilter(resample);
    }


    @Override
    public Evaluation buildModel(AbstractClassifier classifier, Instances training, Instances testing, LearningModelEntity modelEntity){
        try {

            FilteredClassifier fc = new FilteredClassifier();

            if (value.equals(BalancingEnum.SMOTE_SAMPLING)) {
                initSmote(fc, training);
                modelEntity.setBalancing("SMOTE-sampling");
            } else if (value.equals(BalancingEnum.OVER_SAMPLING)) {
                initOverSampling(fc, training);
                modelEntity.setBalancing("OVER-sampling");
            } else {
                initUnderSampling(fc);
                modelEntity.setBalancing("UNDER-sampling");

            }

            fc.setClassifier(classifier);


            Evaluation eval = this.getValidation().buildModel(fc, training, testing, modelEntity);

            if(eval != null) eval.evaluateModel(fc, testing);


            return eval;

        }catch (Exception e){
            logger.log(Level.SEVERE,"Error in balancing evaluation",e);
        }

        return null;
    }
}
