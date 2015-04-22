<meta http-equiv="content-type" content="text/html; charset=UTF-8">
                 
<#import "/inc/select.ftl" as my/>
                 
<@my.genSelect id="address" datas=["黑龙江","哈尔滨","道里区"]/>
                 
<@my.genSelect id="sex" datas=["选择性别","男","女"] defaultValue="男"/>
                 
<@my.genSelect id="sex" datas={"1":"男", "0":"女"} defaultValue="0"/>
                 
<@my.genSelect id="user" datas=userList key="id" text="name"/>
                 
<@my.genSelect id="user" datas=userList key="id" text="name" defaultValue="22"/>
                 
<@my.genSelect id="user" datas=userList key="id" text="name" headKey="-1" headText="选择用户"/>
                 
<@my.genSelect id="stu" datas=stuList key="stuNo" text="stuName" headKey="-1" headText="选择学生"/>