package com.rtsoju.mongle.presentation.view.statistics

import android.content.res.ColorStateList
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.github.mikephil.charting.animation.Easing
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.charts.PieChart
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.*
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet
import com.rtsoju.mongle.R
import com.rtsoju.mongle.databinding.FragmentStatisticsBinding
import com.rtsoju.mongle.databinding.ListitemChartLabelBinding
import com.rtsoju.mongle.domain.model.Emotion
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class StatisticsFragment : Fragment() {

    private val viewModel by viewModels<StatisticsViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = FragmentStatisticsBinding.inflate(inflater, container, false)
        binding.viewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner

        binding.linechartStatistics.run {
            description.isEnabled = false
            isDoubleTapToZoomEnabled = false
            disableScroll()
            setScaleEnabled(false)
            setDrawGridBackground(false)
            setPinchZoom(false)

            xAxis.setDrawAxisLine(false)
            xAxis.position = XAxis.XAxisPosition.BOTTOM
            xAxis.setLabelCount(10, true)

            axisLeft.isEnabled = false
            axisLeft.setDrawGridLines(false)
            axisLeft.axisMaximum = 100f
            axisLeft.axisMinimum = 0f
            axisRight.isEnabled = false

            legend.isEnabled = false

            animateX(300)
            setData(this, 10, 100)
        }

        binding.piechartStatistics.run {
            setUsePercentValues(false)
            setHoleColor(resources.getColor(R.color.background, context.theme))

            description.isEnabled = false
            legend.isEnabled = false
            isDrawHoleEnabled = true
            isRotationEnabled = true
            isHighlightPerTapEnabled = false
            transparentCircleRadius = 60f
            holeRadius = 60f
            rotationAngle = 0f

            animateY(1000, Easing.EaseInOutQuad)
            setDrawEntryLabels(false)
            setData(this, 30)
        }

        createChartLabels(binding)
        return binding.root
    }

    private fun createChartLabels(binding: FragmentStatisticsBinding) {
        val context = requireContext()
        for (emotion in Emotion.values()) {
            val color = context.resources.getColor(emotion.colorRes, context.theme)
            val label = context.resources.getString(emotion.labelRes)
            val labelView = ListitemChartLabelBinding.inflate(layoutInflater).apply {
                dotChartLabel.imageTintList = ColorStateList.valueOf(color)
                textChartLabel.text = label
                textChartLabelCount.text = "800개 (100%)"

            }
            binding.layoutStatisticsChartLabels.addView(labelView.root)
        }
    }

    private fun setData(chart: LineChart, count: Int, range: Int) {
        val values = ArrayList<Entry>()
        for (i in 0 until count) {
            val value = (Math.random() * range).toFloat()
            values.add(Entry(i.toFloat(), value))
        }
        val set1: LineDataSet
        if (chart.data != null && chart.data.dataSetCount > 0) {
            set1 = chart.data.getDataSetByIndex(0) as LineDataSet
            set1.values = values
            set1.notifyDataSetChanged()
            chart.data.notifyDataChanged()
            chart.notifyDataSetChanged()
        } else {
            set1 = LineDataSet(values, "")
            set1.setDrawIcons(false)

            // black lines and points
            val pointColor = resources.getColor(R.color.point, requireContext().theme)
            set1.color = pointColor
            set1.setCircleColor(pointColor)

            // line thickness and point size
            set1.lineWidth = 1f
            set1.circleRadius = 1.5f

            // draw points as solid circles
            set1.setDrawCircleHole(false)

            // customize legend entry
            set1.formLineWidth = 1f
            set1.formSize = 15f

            // text size of values
            set1.setDrawValues(false)
            val dataSets = ArrayList<ILineDataSet>()
            dataSets.add(set1) // add the data sets

            // create a data object with the data sets
            val data = LineData(dataSets)
            data.isHighlightEnabled = false

            // set data
            chart.data = data
        }
        chart.xAxis.setLabelCount(count, true)
    }

    private fun setData(chart: PieChart, range: Int) {
        val entries: ArrayList<PieEntry> = ArrayList()
        val colors: ArrayList<Int> = ArrayList()

        for (emotion in Emotion.values()) {
            colors.add(resources.getColor(emotion.colorRes, requireContext().theme))
            entries.add(
                PieEntry(
                    (Math.random() * range + range / 5).toFloat(),
                    resources.getString(emotion.labelRes)
                )
            )
        }

        val dataSet = PieDataSet(entries, "")
        dataSet.setDrawIcons(false)
        dataSet.valueTextSize = 0f
        dataSet.colors = colors

        val data = PieData(dataSet)
        chart.data = data
        chart.invalidate()
    }
}