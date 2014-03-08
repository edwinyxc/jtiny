###TODO List

> ParamMap HashMap<String,String>

> RespMap HashMap<String,Object>

> req (from broswer) :  
1. form->ParamMap  
2. json->JsonObject <==> Object  
3. Blob->BufferedInputStream  

> resp (to broswer) :  
1. raw String  
2. json JsonObject <==> Object  
3. Blob OutputStream  


> Module:  
* server: (httpd)  
* rt: (ResourceTree)  
* mountPoint: String  

> Controller:  
* servletCtx:  
* AppCtx:  

>> JsonController

> Model:  
* Bo <==> JsonObject
* Repo (embeded Dao)  
>>(query,save,delete,refine)  
* Factory  
>>(create)  
* Logic  
>>(business logic)  




