package org.learning.bookkeeperlearning;

import org.learning.bookkeeperlearning.controller.BalancingDecorator;
import org.learning.bookkeeperlearning.controller.FeatureSelectionDecorator;
import org.learning.bookkeeperlearning.controller.Validation;
import org.learning.bookkeeperlearning.controller.WalkForwardStd;
import org.learning.bookkeeperlearning.entity.LearningModelEntity;
import org.learning.bookkeeperlearning.utility.BalancingEnum;
import org.learning.bookkeeperlearning.utility.BoxChart;
import org.learning.bookkeeperlearning.utility.CsvOutput;
import org.learning.bookkeeperlearning.utility.MetricsEnum;
import weka.core.converters.ConverterUtils.DataSource;

import java.util.ArrayList;
import java.util.List;

public class WekaLearning {


    public static void main(String[] args) throws Exception {
        //load datasets

        DataSource source1 = new DataSource(".\\Deliverable2\\src\\main\\resources\\StormClassBugginessTraining.arff");
        DataSource source2 = new DataSource(".\\Deliverable2\\src\\main\\resources\\StormClassBugginessTesting.arff");

        Validation walkForwardWithBalancingAndFeatureSelection = new FeatureSelectionDecorator(new BalancingDecorator(new WalkForwardStd(source1,source2),BalancingEnum.SMOTE_SAMPLING));

        List<LearningModelEntity> res = new ArrayList<>(walkForwardWithBalancingAndFeatureSelection.validation());

        CsvOutput.addLines(res,source1.getDataSet().size());
        CsvOutput.getWriter().close();

        BoxChart chart  = walkForwardWithBalancingAndFeatureSelection.showChart(res,MetricsEnum.ACCURACY);

        walkForwardWithBalancingAndFeatureSelection.saveChart(chart,"Smote and Feature selection");




    }

}


