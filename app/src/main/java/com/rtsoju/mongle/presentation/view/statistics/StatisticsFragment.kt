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
import com.github.mikephil.charting.formatter.ValueFormatter
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet
import com.rtsoju.mongle.R
import com.rtsoju.mongle.databinding.FragmentStatisticsBinding
import com.rtsoju.mongle.databinding.ListitemChartLabelBinding
import com.rtsoju.mongle.domain.model.StatisticsResult
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

        viewModel.attachDefaultHandlers(requireActivity())
        initializeLineChart(binding)
        initializePieChart(binding)

        viewModel.emotionCount.observe(viewLifecycleOwner) {
            setPieChartData(binding.piechartStatistics, it)
            recreateChartLabels(binding, it)
        }

        viewModel.scoreStatistics.observe(viewLifecycleOwner) {
            setLineChartData(binding.linechartStatistics, it)
        }

        viewModel.updateStatistics()
        return binding.root
    }

    private fun initializePieChart(binding: FragmentStatisticsBinding) {
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
        }
    }

    private fun initializeLineChart(binding: FragmentStatisticsBinding) {
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
        }
    }

    private fun recreateChartLabels(
        binding: FragmentStatisticsBinding,
        datas: List<EmotionChartData>
    ) {
        val context = requireContext()
        binding.layoutStatisticsChartLabels.removeAllViews()

        for (data in datas) {
            val color = context.resources.getColor(data.getColorRes(), context.theme)
            val label = context.resources.getString(data.getLabelRes())
            val labelView = ListitemChartLabelBinding.inflate(layoutInflater).apply {
                dotChartLabel.imageTintList = ColorStateList.valueOf(color)
                textChartLabel.text = label
                textChartLabelCount.text = data.toString()
            }
            binding.layoutStatisticsChartLabels.addView(labelView.root)
        }
    }

    private fun setLineChartData(chart: LineChart, datas: List<StatisticsResult.Score>) {
        val values = ArrayList<Entry>()
        for (i in datas.indices) {
            val score = datas[i].score
            values.add(Entry(i.toFloat(), score ?: 0f, score == null))
        }

        val dataSet: LineDataSet
        if (chart.data != null && chart.data.dataSetCount > 0) {
            dataSet = chart.data.getDataSetByIndex(0) as LineDataSet
            dataSet.values = values
            dataSet.notifyDataSetChanged()
            chart.data.notifyDataChanged()
            chart.notifyDataSetChanged()
            chart.invalidate()
        } else {
            dataSet = LineDataSet(values, "")
            dataSet.setDrawIcons(false)

            // black lines and points
            val pointColor = resources.getColor(R.color.point, requireContext().theme)
            dataSet.color = pointColor
            dataSet.setCircleColor(pointColor)

            // line thickness and point size
            dataSet.lineWidth = 1f
            dataSet.circleRadius = 1.5f

            // draw points as solid circles
            dataSet.setDrawCircleHole(false)

            // customize legend entry
            dataSet.formLineWidth = 1f
            dataSet.formSize = 15f

            // text size of values
            dataSet.setDrawValues(false)

            val dataSets = ArrayList<ILineDataSet>()
            dataSets.add(dataSet) // add the data sets

            // create a data object with the data sets
            val data = LineData(dataSets)
            data.isHighlightEnabled = false

            // set data
            chart.data = data
        }

        chart.xAxis.setLabelCount(datas.size, true)
        chart.xAxis.valueFormatter = object : ValueFormatter() {
            override fun getFormattedValue(value: Float): String {
                val idx = value.toInt()
                if (idx !in datas.indices) {
                    return value.toString()
                }
                return datas[idx].label
            }
        }
    }

    private fun setPieChartData(chart: PieChart, datas: List<EmotionChartData>) {
        val entries: ArrayList<PieEntry> = ArrayList()
        val colors: ArrayList<Int> = ArrayList()

        for (data in datas) {
            colors.add(resources.getColor(data.getColorRes(), requireContext().theme))
            entries.add(
                PieEntry(
                    data.proportion,
                    resources.getString(data.getLabelRes())
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