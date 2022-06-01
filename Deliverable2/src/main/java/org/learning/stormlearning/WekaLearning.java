package org.learning.stormlearning;

import org.learning.stormlearning.controller.BalancingDecorator;
import org.learning.stormlearning.controller.FeatureSelectionDecorator;
import org.learning.stormlearning.controller.Validation;
import org.learning.stormlearning.controller.WalkForwardStd;
import org.learning.stormlearning.entity.LearningModelEntity;
import org.learning.stormlearning.utility.BalancingEnum;
import org.learning.stormlearning.utility.BoxChart;
import org.learning.stormlearning.utility.CsvOutput;
import org.learning.stormlearning.utility.MetricsEnum;
import weka.core.converters.ConverterUtils.DataSource;

import java.util.ArrayList;
import java.util.List;

public class WekaLearning {


    public static void main(String[] args) throws Exception {
        //load datasets

        DataSource source1 = new DataSource(".\\Deliverable2\\src\\main\\resources\\StormClassBugginessTraining.arff");
        DataSource source2 = new DataSource(".\\Deliverable2\\src\\main\\resources\\StormClassBugginessTesting.arff");

        Validation walkForwardStd = new WalkForwardStd(source1,source2);

        List<LearningModelEntity> res = new ArrayList<>(walkForwardStd.validation());

        CsvOutput.addLines(res,source1.getDataSet().size());
        CsvOutput.getWriter().close();

        Validation walkForwardWithBalancing = new BalancingDecorator(new WalkForwardStd(source1,source2), BalancingEnum.SMOTE_SAMPLING);

        res.addAll(walkForwardWithBalancing.validation());

        Validation walkForwardWithBalancingAndFeatureSelection = new FeatureSelectionDecorator(new BalancingDecorator(new WalkForwardStd(source1,source2),BalancingEnum.SMOTE_SAMPLING));

        res.addAll(walkForwardWithBalancingAndFeatureSelection.validation());

        BoxChart chart = walkForwardWithBalancingAndFeatureSelection.showChart(res, MetricsEnum.ACCURACY);

        walkForwardWithBalancingAndFeatureSelection.saveChart(chart,"Accuracy_All");




    }

}

