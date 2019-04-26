<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" isELIgnored="false" %>
<!-- 为ECharts准备一个具备大小（宽高）的Dom -->
<body style="text-align: center">
<div id="statistics_china" style="width: 1000px;height: 500px;margin-top: 30px;margin-left: 30px">
    <script>
        // 基于准备好的dom，初始化echarts实例
        var myChart = echarts.init(document.getElementById('statistics_china'));

        function randomData() {
            return Math.round(Math.random() * 1000);
        }

        option = {
            title: {
                text: '持名法州APP用户分布图',
                subtext: '2019年4月23日 最新数据',
                left: 'center'
            },
            tooltip: {
                trigger: 'item'
            },
            // 说明
            legend: {
                orient: 'vertical',
                left: 'left',
                data: ['男', '女']
            },
            visualMap: {
                min: 0,
                max: 50,
                left: 'left',
                top: 'bottom',
                text: ['高', '低'],           // 文本，默认为数值文本
                calculable: true
            },
            // 工具箱
            toolbox: {
                show: true,
                orient: 'vertical',
                left: 'right',
                top: 'center',
                feature: {
                    dataView: {readOnly: false},
                    restore: {},
                    saveAsImage: {}
                }
            },
            series: [
                {
                    name: '男',
                    type: 'map',
                    mapType: 'china',
                    roam: false,
                    label: {
                        normal: {
                            show: true
                        },
                        emphasis: {
                            show: true
                        }
                    },
                    data: []
                },
                {
                    name: '女',
                    type: 'map',
                    mapType: 'china',
                    label: {
                        normal: {
                            show: true
                        },
                        emphasis: {
                            show: true
                        }
                    },
                    data: []
                }
            ]
        };
        myChart.setOption(option);

        $(function () {
            $.post("${pageContext.request.contextPath}/user/selectMaleDistribution", function (data) {
                //console.log(data);
                myChart.setOption({
                    series: [{
                        // 根据名字对应到相应的系列
                        name: '男',
                        data: data
                    }]
                });
            }, "json");

            $.post("${pageContext.request.contextPath}/user/selectFemaleDistribution", function (data) {
                //console.log(data);
                myChart.setOption({
                    series: [{
                        // 根据名字对应到相应的系列
                        name: '女',
                        data: data
                    }]
                });
            }, "json");
        });


    </script>


</div>
</body>