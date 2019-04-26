<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" isELIgnored="false" %>

<script>
    $(function () {
        $.ajax({
            type: 'post',
            url: '${pageContext.request.contextPath}/user/selectNearlyThreeWeekRegistUser',
            dataType: 'JSON',
            success: function (data) {
                // 基于准备好的dom，初始化echarts实例
                var myChart = echarts.init(document.getElementById('main'));
                // 指定图表的配置项和数据
                var option = {
                    title: {
                        text: '持明法洲近三周注册用户数量'
                    },
                    tooltip: {},
                    legend: {
                        data: [{
                            name: "注册数量",
                            icon: 'diamond',
                            // 设置文本为红色
                            textStyle: {
                                color: 'black'
                            }
                        }],
                        padding: [
                            5,  // 上
                            10, // 右
                            5,  // 下
                            10, // 左
                        ]
                    },
                    xAxis: {
                        data: ["一周", "两周", "三周"]
                    },
                    yAxis: {},
                    series: [{
                        name: '注册数量',
                        type: 'bar',
                        data: [parseInt(data.a), parseInt(data.b), parseInt(data.c)]
                    }, {
                        name: '注册数量',
                        type: 'line',
                        color: '#096',
                        data: [parseInt(data.a), parseInt(data.b), parseInt(data.c)]
                    }]
                };
                // 使用刚指定的配置项和数据显示图表。
                myChart.setOption(option);
            }
        })

        var goEasy = new GoEasy({
            appkey: "BS-4b4549e8f45a4350935ea4f1d5aa91a1"
        });
        goEasy.subscribe({
            channel: "wq",
            onMessage: function (message) {
                // alert("Channel:" + message.channel + " content:" + message.content);
                var ids = JSON.parse(message.content);
                var myChart = echarts.init(document.getElementById('main'));
                // 指定图表的配置项和数据
                var option = {
                    title: {
                        text: '持明法洲近三周注册用户数量'
                    },
                    tooltip: {},
                    legend: {
                        data: [{
                            name: "注册数量",
                            icon: 'diamond',
                            // 设置文本为红色
                            textStyle: {
                                color: 'black'
                            }
                        }],
                        padding: [
                            5,  // 上
                            10, // 右
                            5,  // 下
                            10, // 左
                        ]
                    },
                    xAxis: {
                        data: ["一周", "两周", "三周"]
                    },
                    yAxis: {},
                    series: [{
                        name: '注册数量',
                        type: 'bar',
                        data: [parseInt(ids.a), parseInt(ids.b), parseInt(ids.c)]
                    }, {
                        name: '注册数量',
                        type: 'line',
                        color: '#096',
                        data: [parseInt(ids.a), parseInt(ids.b), parseInt(ids.c)]
                    }]
                };
                // 使用刚指定的配置项和数据显示图表。
                myChart.setOption(option);
            }
        });
    })


</script>

<!-- 为 ECharts 准备一个具备大小（宽高）的 DOM -->
<div id="main" style="width: 600px;height:400px;"></div>