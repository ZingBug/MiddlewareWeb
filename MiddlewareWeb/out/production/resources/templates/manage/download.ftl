<!doctype html>
<html lang="en">
<head>
    <meta charset="UTF-8" content="text/html">
    <meta name="viweport" content="width=device-width,initial-scale=1">

    <!--jquery-->
    <script type="text/javascript" src="/jquery/jquery-3.2.1.min.js"></script>

    <!-- Bootstrap core CSS -->
    <link rel="stylesheet" href="/bootstrap-3.3.7/css/bootstrap.min.css" type="text/css" >
    <script src="/bootstrap-3.3.7/js/bootstrap.min.js"></script>

    <!--datetimepicker-->
    <script src="/js/moment-with-locales.min.js" type="text/javascript"></script>
    <link rel="stylesheet" href="/css/bootstrap-datetimepicker.min.css" type="text/css">
    <script src="/js/bootstrap-datetimepicker.min.js" type="text/javascript"></script>

    <!--Bootstrap Table-->
    <link rel="stylesheet" href="/css/bootstrap-table.min.css" type="text/css">
    <script src="/js/bootstrap-table.min.js" type="text/javascript"></script>
    <script src="/js/bootstrap-table-zh-CN.min.js" type="text/javascript"></script>

    <!--IE10 viewport hack for Surface/desktop Windows 8 bug-->
    <link rel="stylesheet" href="/css/ie10-viewport-bug-workaround.css" type="text/css">
    <script src="/js/ie10-viewport-bug-workaround.js" type="text/javascript"></script>

    <!--Sidebar style-->
    <link rel="stylesheet" href="/css/style-sidebar.css" type="text/css">

    <!--Validator-->
    <link rel="stylesheet" type="text/css" href="/css/bootstrapValidator.min.css">
    <script type="text/javascript" src="/js/bootstrapValidator.min.js"></script>

    <!--Form To JSON-->
    <script type="text/javascript" src="/jquery/jquery.serializejson.js"></script>

    <style rel="stylesheet" type="text/css" >
        .selectContainer .form-control-feedback {
            top: 0;
            margin-right: -35px;
        }
    </style>
</head>
<body>
<div class="container">
    <form id="downFrom" class="form-horizontal" method="post" action="" target="hidden_frame">

        <div class="form-group">
            <label class="col-lg-3 control-label">样本ID</label>
            <div class="col-lg-5">
                <input type="text" class="form-control" name="sampleId" placeholder="样本ID"/>
            </div>
        </div>

        <div class="form-group">
            <label class="col-lg-3 control-label">病人姓名</label>
            <div class="col-lg-5">
                <input type="text" class="form-control" name="firstName" placeholder="病人姓名"/>
            </div>
        </div>

        <div class="form-group">
            <label class="col-lg-3 control-label">病人ID</label>
            <div class="col-lg-5">
                <input type="text" class="form-control" name="patientId" placeholder="病人ID"/>
            </div>
        </div>

        <div class="form-group">
            <label class="col-lg-3 control-label">病人性别</label>
            <div class="col-lg-2 selectContainer">
                <select class="form-control" name="sex" data-live-search="true">
                    <option value="M">男</option>
                    <option value="F">女</option>
                </select>
            </div>
        </div>

        <div class="form-group">
            <label class="col-lg-3 control-label">病人年龄</label>
            <div class="col-lg-2">
                <input type="number" class="form-control" name="age" placeholder="年龄"/>
            </div>
        </div>

        <div class="form-group">
            <label class="col-lg-3 control-label">检验仪器</label>
            <div class="col-lg-2 selectContainer">
                <select class="form-control" id="device" name="device">
                </select>
            </div>
        </div>

        <div class="form-group">
            <label class="col-lg-3 control-label">样本类型</label>
            <div class="col-lg-5">
                <div class="radio">
                    <label>
                        <input type="radio" name="kind" value="bio">生化
                    </label>
                </div>
                <div class="radio">
                    <label>
                        <input type="radio" name="kind" value="ele">电解质
                    </label>
                </div>
            </div>
        </div>

        <div class="form-group">
            <label class="col-lg-3 control-label">急诊类型</label>
            <div class="col-lg-5">
                <div class="radio">
                    <label>
                        <input type="radio" name="emergency" value="1">是
                    </label>
                </div>
                <div class="radio">
                    <label>
                        <input type="radio" name="emergency" value="0">否
                    </label>
                </div>
            </div>
        </div>

        <div class="form-group">
            <label class="col-lg-3 control-label">检验项目</label>
            <div class="col-lg-2">
                <input class="form-control" type="number" name="itemIndex[0]" placeholder="项目编号"/>
            </div>
            <div class="col-lg-2">
                <input class="form-control" type="text" name="itemName[0]" placeholder="项目名称"/>
            </div>
            <div class="col-lg-2">
                <button type="button" class="btn btn-default addButton"><i class="glyphicon glyphicon-plus"></i> </button>
            </div>
        </div>

        <div class="form-group hide" id="itemTemplate">
            <label class="col-lg-3 control-label">检验项目</label>
            <div class="col-lg-2">
                <input class="form-control" type="number" name="indexs" placeholder="项目编号"/>
            </div>
            <div class="col-lg-2">
                <input class="form-control" type="text" name="names" placeholder="项目名称"/>
            </div>
            <div class="col-lg-2">
                <button type="button" class="btn btn-default removeButton"><i class="glyphicon glyphicon-minus"></i> </button>
            </div>
        </div>

        <div class="form-group">
            <label class="col-lg-3 control-label" id="captchaOperation"></label>
            <div class="col-lg-2">
                <input type="text" class="form-control" name="captcha"/>
            </div>
        </div>

        <div class="form-group">
            <div class="col-lg-9 col-lg-offset-3">
                <button type="submit" class="btn btn-primary" id="download">下发</button>
            </div>
        </div>

    </form>
    <iframe name="hidden_frame" id="hidden_frame" style="display:none"></iframe>
</div>
<script type="text/javascript">
    $(function () {
        getDevice();
    });
    function getDevice() {
        //获得下发仪器
        $.ajax({
            type:"GET",
            url:"manage/download/getDevice",
            dataType:"json",
            contentType:"application/json;charset=utf-8",
            async:true,
            success:function (jsonText) {
                var str="";
                for(var key in jsonText)
                {
                    if(jsonText.hasOwnProperty(key))
                    {
                        str+='<option>'+jsonText[key]+'</option>'
                    }
                }
                //$('.selectpicker').html(str);
                $('#device').html(str);
            }
        })
    }
    $(document).ready(function () {
        function randomNumber(min,max) {
            return Math.floor(Math.random()*(max-min+1)+min);
        }
        refreshCaptcha();
        function refreshCaptcha() {
            $('#captchaOperation').html([randomNumber(1,100),'+',randomNumber(1,200),'='].join(' '));
        }
        $("#downFrom").submit(function (ev) {
            ev.preventDefault();
        });
        $('#download').on("click",function () {
            var bootstrapValidator=$('#downFrom').data('bootstrapValidator');
            bootstrapValidator.validate();
            if(bootstrapValidator.isValid())
            {

                //向后台下发
                var downData=$('#downFrom').serializeJSON();

                $.ajax({
                    type:"POST",
                    url:"manage/download/downData",
                    data:JSON.stringify(downData),
                    contentType:"application/json;charset=utf-8",
                    success:function (data) {
                        alert("下发成功");
                        //清空表格
                        $('#downFrom')[0].reset();
                        refreshCaptcha();//刷新验证码
                        getDevice();//重新得到仪器
                        //重新加载此页面
                        //document.location.reload();
                    },
                    error:function (data) {
                        refreshCaptcha();
                        alert("下发失败");
                    }
                })
            }
            else
            {
                refreshCaptcha();
                return;
            }
        });

        var itemIndex=0;//项目索引号
        $('#downFrom').on('click','.addButton',function () {
            itemIndex++;
            var $template=$('#itemTemplate'),
                    $clone=$template
                            .clone()
                            .removeClass('hide')
                            .removeAttr('id')
                            .attr('item-index',itemIndex)
                            .insertBefore($template);
            $clone
                    .find('[name="indexs"]').attr('path','itemIndex['+itemIndex+']').attr('name','itemIndex['+itemIndex+']')
                    .click(function () {
                        var elements=$("input[name^='itemIndex']");
                        var diffstr=new Array();
                        for(var i=0;i<elements.length-1;i++)
                        {
                            diffstr.push(elements[i].name);
                        }
                        $('#downFrom').data('bootstrapValidator').addField('itemIndex['+itemIndex+']',{
                            validators:{
                                notEmpty:{
                                    message:'项目编号不能为空'
                                },
                                integer:{
                                    message:'编号必须为整数',
                                    thousandsSeparator: '',
                                    decimalSeparator: '.'
                                },
                                greaterThan:{
                                    value:0,
                                    message:'编号必须大于0'
                                },
                                different: {
                                    field: diffstr.toString(),
                                    message: '不能有相同的项目编号'
                                }
                            }
                        })
                    })
                    .end()
                    .find('[name="names"]').attr('path','itemName['+itemIndex+']').attr('name','itemName['+itemIndex+']').end();
        }).on('click','.removeButton',function () {
            var $row=$(this).parents('.form-group');
            $row.remove();
        });

        $('#downFrom').bootstrapValidator({
            message:'This value is not valid',
            feedbackIcons:{
                valid:'glyphicon glyphicon-ok',
                invalid:'glyphicon glyphicon-remove',
                validating:'glyphicon glyphicon-refresh'
            },
            fields:{
                sampleId:{
                    message:'This sampleId is not valid',
                    validators:{
                        notEmpty:{
                            message:'样本ID不能为空'
                        }
                    }
                },
                firstName:{
                    message:'This name is not valid',
                    validators:{
                        notEmpty:{
                            message:'病人姓名不能为空'
                        }
                    }
                },
                patientId:{
                    message:'This patientId is not valid',
                    validators:{
                        notEmpty:{
                            message:'病人ID不能为空'
                        }
                    }
                },
                sex:{
                    message:'This sex is not valid',
                    validators:{
                        notEmpty:{
                            message:'病人性别不能为空'
                        }
                    }
                },
                device:{
                    message:'This device is not valid',
                    validators:{
                        notEmpty:{
                            message:'当前未有仪器连接'
                        }
                    }
                },
                age:{
                    message:'This age is not valid',
                    validators:{
                        notEmpty:{
                            message:'病人年龄不能为空'
                        },
                        digits:{
                            message:'年龄必须为整数',
                            thousandsSeparator: '',
                            decimalSeparator: '.'
                        },
                        greaterThan:{
                            value:1,
                            message:'年龄过小'
                        },
                        lessThan:{
                            value:120,
                            message:'年龄过大'
                        }
                    }
                },
                kind:{
                    validators:{
                        choice:{
                            min:1,
                            max:1,
                            message:'请选择项目类型'
                        }
                    }
                },
                emergency:{
                    validators:{
                        choice:{
                            min:1,
                            max:1,
                            message:'请选择急诊类型'
                        }
                    }
                },
                'itemIndex[0]':{
                    message:'This index is not valid',
                    validators:{
                        notEmpty:{
                            message:'项目编号不能为空'
                        },
                        greaterThan:{
                            value:0,
                            message:'编号必须大于0'
                        },
                        integer:{
                            message:'编号必须为整数',
                            thousandsSeparator: '',
                            decimalSeparator: '.'
                        }
                    }
                },
                captcha:{
                    validators:{
                        callback:{
                            message:'验证错误',
                            callback:function (value,validator) {
                                var item=$('#captchaOperation').html().split(' ');
                                var sum=parseInt(item[0])+parseInt(item[2]);
                                return value==sum;
                            }
                        }
                    }
                }
            }
        });
    })
</script>
</body>
</html>