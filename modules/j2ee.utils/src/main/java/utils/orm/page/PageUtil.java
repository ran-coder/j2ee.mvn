package utils.orm.page;


public class PageUtil {
	private int page;
	private int totalpage;
	//private int count;
	public PageUtil(){}
	public PageUtil(int page,int totalpage){
		this.page=page;
		this.totalpage=totalpage;
	}
	public PageUtil(int page,int totalpage,int count){
		this.page=page;
		this.totalpage=totalpage;
		//this.count=count;
	}
	public String pageBox(){
		return toString();
	}
	public String pageBox5(){
		StringBuffer pageTool= new StringBuffer("");//分页
		if(totalpage>1 && page<=totalpage && page>0){
			String firstpage="<font face='webdings' Title='First Page'>9</font>",
			lastpage="<font face='webdings' Title='Prev Page'>3</font>",
			nextpage="<font face='webdings' Title='Next Page'>4</font>",
			endpage="<font face='webdings' Title='Last Page'>:</font>";
			pageTool.append("[").append(page).append("/").append(totalpage).append("]  \n");
			if(page>1){//显示上一页
				pageTool
					.append("<a href='javascript:void(0)' onclick='go2Page(1);return false;'>").append(firstpage).append("</a> \n")
					.append("<a href='javascript:void(0)' onclick='go2Page(").append(page-1)
					.append(");return false;'>").append(lastpage).append("</a> \n");
			}else{
				pageTool.append(firstpage).append(" <a>").append(lastpage).append("</a> \n");
			}

			if((page-1)/5<=totalpage/5){//显示中间分页
				for(int i=(page-1)/5*5+1;i<=(page-1)/5*5+5 && i<=totalpage;i++){
					if(i!=page){
						pageTool.append("<a href='javascript:void(0)' onclick='go2Page(")
							.append(i)
							.append(");return false;'> ")
							.append(i)
							.append("</a>\n");
					}else{
						pageTool.append("<a><font color=red>")
							.append(i)
							.append("</font></a>\n");
					}
				}
			}

			if(page==totalpage){//显示下一页
				pageTool.append(nextpage).append(" ").append(endpage);
			}else{
				pageTool.append("<a href='javascript:void(0)' onclick='go2Page(").append(page+1)
					.append(");return false;'>").append(nextpage).append("</a>\n")
					.append("<a href='javascript:void(0)' onclick='go2Page(").append(totalpage)
					.append(");return false;'>").append(endpage).append("</a>\n");
			}
		}
		return pageTool.toString();
	}
	
	public String pageBoxest(int ipage,int all){
		StringBuffer pageBox=new StringBuffer("");
		if(all>1 &&  ipage<=all && ipage>0){
			String firstpage="&lt;&lt;&lt",
			lastpage="上一页",
			nextpage="下一页",
			endpage="&gt;&gt;&gt;";
			pageBox.append("[").append(ipage).append("/").append(all).append("]  \n");
			if(ipage<=1){
				pageBox.append(firstpage).append("\n ")
					.append(lastpage).append("\n");
			}else{
				pageBox.append(" <a href='javascript:void(0)' onclick='go2Page(1);return false;' >").append(firstpage).append("</a> \n")
					.append(" <a href='javascript:void(0)' onclick='go2Page(").append(ipage-1)
					.append(");return false;'>").append(lastpage).append("</a>\n");
			}

			if((ipage-2)>0)
				pageBox.append(" <a href='javascript:void(0)' onclick='go2Page(")
				.append(ipage-2).append(");return false;'>").append(ipage-2).append("</a>\n");
			if((ipage-1)>0)
				pageBox.append(" <a href='javascript:void(0)' onclick='go2Page(")
				.append(ipage-1).append(");return false;'>").append(ipage-1).append("</a>\n");
			pageBox.append(" <font color=red>").append(ipage).append("</font>");

			if((ipage+1)<=all)
				pageBox.append(" <a href='javascript:void(0)' onclick='go2Page(")
				.append(ipage+1).append(");return false;'>").append(ipage+1).append("</a>\n");
			if((ipage+2)<=all)
				pageBox.append(" <a href='javascript:void(0)' onclick='go2Page(")
				.append(ipage+2).append(");return false;'>").append(ipage+2).append("</a>\n");

			if((ipage+1)<=all){
				pageBox.append(" <a href='javascript:void(0)' onclick='go2Page(")
				.append(ipage+1).append(");return false;'>").append(nextpage).append("</a>\n");
				pageBox.append(" <a href='javascript:void(0)' onclick='go2Page(")
				.append(all).append(");return false;' title='末页'>").append(endpage).append("</a>\n");
			}else{
				pageBox.append(" 下一页\n");
				pageBox.append(" &gt;&gt;&gt;\n");
			}
			
		}
		return pageBox.toString();
	}

	
	public String toString(){
		StringBuffer pageTool= new StringBuffer("");//分页
		if(totalpage>1 && page<=totalpage && page>0){
			String firstpage="<font face='webdings'>9</font>",
			lastpage="<font face='webdings'>3</font>",
			nextpage="<font face='webdings'>4</font>",
			endpage="<font face='webdings'>:</font>";
			pageTool.append("[").append(page).append("/").append(totalpage).append("]  \n");
			if(page>1){//显示上一页
				pageTool
					.append("<a href='javascript:void(0)' onclick='go2Page(1);return false;'>").append(firstpage).append("</a> \n")
					.append("<a href='javascript:void(0)' onclick='go2Page(").append(page-1)
					.append(");return false;'>").append(lastpage).append("</a> \n");
			}else{
				pageTool.append(firstpage).append(" <a>").append(lastpage).append("</a> \n");
			}

			if((page-1)/5<=totalpage/5){//显示中间分页
				int pages=(page-1)/5*5;
				int count =pages+1;//中间多少个5页
				if(totalpage<pages+5) {
					if(totalpage>5)	count=totalpage-5+1;
					//System.out.println("totalpage="+totalpage+",pages="+pages+",count="+count);
					for(;count>0 && count<=totalpage;count++){
						if(count!=page){
							pageTool.append("<a href='javascript:void(0)' onclick='go2Page(")
								.append(count)
								.append(");return false;'> ")
								.append(count)
								.append("</a>\n");
						}else{
							pageTool.append("<a><font color=red>")
								.append(count)
								.append("</font></a>\n");
						}
					}
				}else {
					for(;count<=pages+5 && count<=totalpage;count++){
						if(count!=page){
							pageTool.append("<a href='javascript:void(0)' onclick='go2Page(")
								.append(count)
								.append(");return false;'> ")
								.append(count)
								.append("</a>\n");
						}else{
							pageTool.append("<a><font color=red>")
								.append(count)
								.append("</font></a>\n");
						}
					}
				}
			}

			if(page==totalpage){//显示下一页
				pageTool.append(nextpage).append(" ").append(endpage);
			}else{
				pageTool.append("<a href='javascript:void(0)' onclick='go2Page(").append(page+1)
					.append(");return false;'>").append(nextpage).append("</a>\n")
					.append("<a href='javascript:void(0)' onclick='go2Page(").append(totalpage)
					.append(");return false;'>").append(endpage).append("</a>\n");
			}
		}
		return pageTool.toString();
	}
	
	public static void main(String[] args) {
		System.out.println(new PageUtil(6,7).toString());
		

	}

}
