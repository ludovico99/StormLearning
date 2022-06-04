package org.learning.stormlearning.entity;

import weka.core.Instances;

import java.util.ArrayList;
import java.util.List;

public class LearningModelEntity {

    private String classifier;
    private String dataSetName;

    private List<Instances> trainings;
    private List<Instances> testings;

    private int iterations;

    private final List<Double> tp;
    private final List<Double> tn;
    private final List<Double> fp;
    private final List<Double> fn;

    private final List<Double> accuracy;
    private final List<Double> recall;
    private final List<Double> precision;
    private final List<Double> kappa;
    private final List<Double> rocAuc;

    private String balancing = "";
    private boolean featureSelection = false;
    private String typeFeatureSelection = "";
    private boolean costSensitive = false;

    @Override
    public String toString() {
        return "LearningModelEntity{" +
                "classifier='" + classifier + '\'' +
                ", dataSetName='" + dataSetName + '\'' +
                ", trainings=" + trainings +
                ", testings=" + testings +
                ", iterations=" + iterations +
                ", tp=" + tp +
                ", tn=" + tn +
                ", fp=" + fp +
                ", fn=" + fn +
                ", accuracy=" + accuracy +
                ", recall=" + recall +
                ", precision=" + precision +
                ", kappa=" + kappa +
                ", rocAuc=" + rocAuc +
                ", balancing='" + balancing + '\'' +
                ", featureSelection=" + featureSelection +
                ", typeFeatureSelection='" + typeFeatureSelection + '\'' +
                ", costSensitive=" + costSensitive +
                '}';
    }

    public String getTypeFeatureSelection() {
        return typeFeatureSelection;
    }

    public void setTypeFeatureSelection(String typeFeatureSelection) {
        this.typeFeatureSelection = typeFeatureSelection;
    }

    public void setFeatureSelection(boolean featureSelection) {
        this.featureSelection = featureSelection;
    }

    public boolean isFeatureSelection() {
        return featureSelection;
    }

    public LearningModelEntity(String classifier,String dataSetName, int iterations) {
        this.classifier = classifier;
        this.dataSetName = dataSetName;
        this.iterations = iterations;
        trainings = new ArrayList<>();
        testings = new ArrayList<>();
        tp = new ArrayList<>();
        tn = new ArrayList<>();
        fp = new ArrayList<>();
        fn = new ArrayList<>();
        accuracy = new ArrayList<>();
        recall = new ArrayList<>();
        precision = new ArrayList<>();
        kappa = new ArrayList<>();
        rocAuc = new ArrayList<>();

    }

    public String getBalancing() {
        return balancing;
    }

    public void setBalancing(String balancing) {
        this.balancing = balancing;
    }

    public int getIterations() {
        return iterations;
    }

    public String getClassifier() {
        return classifier;
    }

    public String getDataSetName() {
        return dataSetName;
    }

    public void setClassifier(String classifier) {
        this.classifier = classifier;
    }

    public void setDataSetName(String dataSetName) {
        this.dataSetName = dataSetName;
    }

    public void setIterations(int iterations) {
        this.iterations = iterations;
    }

    public List<Instances> getTrainings() {
        return trainings;
    }


    public List<Instances> getTestings() {
        return testings;
    }

    public void setTestings(List<Instances> testings) {
        this.testings = testings;
    }

    public void setTrainings(List<Instances> trainings) {
        this.trainings = trainings;
    }

    public List<Double> getTp() {
        return tp;
    }

    public void addTp(Double tp) {
        this.tp.add(tp);
    }

    public List<Double> getTn() {
        return tn;
    }

    public void addTn(Double tn) {
        this.tn.add(tn);
    }

    public List<Double> getFp() {
        return fp;
    }

    public void addFp(Double fp) {
        this.fp.add(fp);
    }

    public List<Double> getFn() {
        return fn;
    }

    public void addFn(Double fn) {
        this.fn.add(fn);
    }

    public List<Double> getAccuracy() {
        return accuracy;
    }

    public void addAccuracy(Double accuracy) {
        this.accuracy.add(accuracy);
    }

    public List<Double> getRecall() {
        return recall;
    }

    public void addRecall(Double recall) {
        this.recall.add(recall);
    }

    public List<Double> getPrecision() {
        return precision;
    }

    public void addPrecision(Double precision) {
        this.precision.add(precision);
    }

    public List<Double> getKappa() {
        return kappa;
    }

    public void addKappa(Double kappa) {
        this.kappa.add(kappa);
    }

    public List<Double> getRocAuc() {
        return rocAuc;
    }

    public void addRocAuc(Double rocAuc) {
        this.rocAuc.add(rocAuc);
    }

    public boolean isCostSensitive() {
        return costSensitive;
    }

    public void setCostSensitive(boolean costSensitive) {
        this.costSensitive = costSensitive;
    }
}
