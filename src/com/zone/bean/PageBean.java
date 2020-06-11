package com.zone.bean;

import java.util.ArrayList;
import java.util.List;

public class PageBean implements BaseBean{

	/**
	 * 如果通过无参初始化PageBean类将得到一个初始值为如下的实体
	 * this.pageID = 0;
	 * recordCount = 0;
	 * recordTotal = 0;
	 * this.listBean = new ArrayList();
	 * this.pageTotal = 0;
	 * recordStart = 0;
	 */
	public PageBean() {
		this.pageID = 1;
		this.recordCount = 1;
		this.recordTotal = 0;
		this.listBean = new ArrayList();
		this.pageTotal = 0;
		this.recordStart = 0;
	}
	
	private Integer pageID = null;
	private Integer recordCount = null;
	private List<?> listBean = null;
	private Integer recordTotal = null;
	
	/**
	 * 默认每页记录数，奇数为1偶数为2
	 */
	private final Integer defaultValue1 = 3;
	private final Integer defaultValue2 = 4;
	
	public PageBean(Integer pageID,Integer recordTotal,Integer recordCount) {
		super();
		//判空
		if ((recordTotal!=null)&&(recordCount!=null)&&(pageID!=null)) {
			//判值
			if ((pageID>0)&&(recordCount>0)&&(recordTotal>0)) {
				this.recordTotal = recordTotal;
				//如果每页记录数超过阈值(数据库中总记录数recordTotal)，则设置记录数count为默认值defaultValue
				if (!(recordCount>recordTotal)) {
					//如果整除大于零，尾页记录条目满
					if (((recordTotal%recordCount)==0)) {
						//如果页码超过阈值(数据库中总记录数recordTotal)，则设置pageID为尾页
						if (!(pageID>recordTotal)) {
							//如果页码与每页记录数相乘超过阈值(数据库中总记录数recordTotal)，
							//保持每页记录数recordCount不变，将页码pageID设置为尾页
							if (!((pageID*recordCount)>recordTotal)) {
								this.pageID = pageID;
								this.recordCount = recordCount;
								this.pageTotal = recordTotal/recordCount;
								if (!(pageID==1)) {
									this.recordStart = (pageID-1)*recordCount;
								}else {
									this.recordStart = 0;
								}
							}else {
								this.pageID = recordTotal/recordCount;
								this.recordCount = recordCount;
								this.pageTotal = recordTotal/recordCount;
								this.recordStart = ((recordTotal/recordCount)-1)*recordCount;
							}
						}else {
							this.pageID = recordTotal/recordCount;
							this.recordCount = recordCount;
							this.pageTotal = recordTotal/recordCount;
							this.recordStart = ((recordTotal/recordCount)-1)*recordCount;
						}	
					}else {
						//页码未过界、记录未过界、有未满页
						if (!(pageID>recordTotal)) {
							if (!((pageID*recordCount)>recordTotal)) {
								this.pageID = pageID;
								if (pageID==((recordTotal/recordCount)+1)) {
									this.recordCount = recordTotal%recordCount;
								}else {
									this.recordCount = recordCount;									
								}
								this.pageTotal = (recordTotal/recordCount)+1;
								this.recordStart = (pageID-1)*recordCount;
							}else {
								this.pageID = recordTotal/recordCount+1;
								this.recordCount = recordTotal%recordCount;
								this.pageTotal = recordTotal/recordCount+1;
								this.recordStart = (recordTotal/recordCount)*recordCount;
							}
						}else {
							//页码过界、记录未过界、有未满页
							//记录数除尾页外不变、页码变为当前记录数最后一页
							this.pageID = recordTotal/recordCount+1;
							this.recordCount = recordTotal%recordCount;
							this.pageTotal = (recordTotal/recordCount)+1;
							this.recordStart = (recordTotal/recordCount)*recordCount;
						}
					}
				}else {//记录数过界
					if (recordTotal%2==1) {
						if (recordTotal>defaultValue1) {
							this.pageID = (recordTotal/defaultValue1)+1;
							this.recordCount = recordTotal%defaultValue1;									
							this.pageTotal = (recordTotal/defaultValue1)+1;
							this.recordStart = (recordTotal/defaultValue1)*defaultValue1;
						}else if (recordTotal==defaultValue1) {
							this.pageID = 1;
							this.recordCount = defaultValue1;									
							this.pageTotal = 1;
							this.recordStart = 0;
						}else {
							this.pageID = 1;
							this.recordCount = recordTotal;									
							this.pageTotal = 1;
							this.recordStart = 0;
						}
					}else {
						if (recordTotal>defaultValue2) {
							this.pageID = recordTotal/defaultValue2;
							this.recordCount = defaultValue2;									
							this.pageTotal = recordTotal/defaultValue2;
							this.recordStart = ((recordTotal/defaultValue2)-1)*defaultValue2;
						}else if (recordTotal==defaultValue2) {
							this.pageID = 1;
							this.recordCount = defaultValue2;									
							this.pageTotal = 1;
							this.recordStart = 0;
						}else {
							this.pageID = 1;
							this.recordCount = recordTotal;									
							this.pageTotal = 1;
							this.recordStart = 0;
						}
					}
				}
			}else {
				if (((pageID<0)||(recordCount<0))&&(recordTotal>0)) {
					if (recordTotal%2==1) {
						if (recordTotal>defaultValue1) {
							this.pageID = 1;
							this.recordCount = defaultValue1;	
							this.recordTotal = recordTotal;
							this.pageTotal = recordTotal/defaultValue1+1;
							this.recordStart = 0;
						}else if (recordTotal==defaultValue1) {
							this.pageID = 1;
							this.recordCount = defaultValue1;
							this.recordTotal = recordTotal;
							this.pageTotal = 1;
							this.recordStart = 0;
						}else {
							this.pageID = 1;
							this.recordCount = recordTotal;
							this.recordTotal = recordTotal;
							this.pageTotal = 1;
							this.recordStart = 0;
						}
					}else {
						if (recordTotal>defaultValue2) {
							this.pageID = 1;
							this.recordCount = defaultValue2;	
							this.recordTotal = recordTotal;
							this.pageTotal = recordTotal/defaultValue2;
							this.recordStart = 0;
						}else if (recordTotal==defaultValue2) {
							this.pageID = 1;
							this.recordCount = defaultValue2;	
							this.recordTotal = recordTotal;
							this.pageTotal = 1;
							this.recordStart = 0;
						}else {
							this.pageID = 1;
							this.recordCount = recordTotal;	
							this.recordTotal = recordTotal;
							this.pageTotal = 1;
							this.recordStart = 0;
						}
					}
				}else {
					this.pageID = 1;
					this.recordCount = 1;
					this.recordTotal = Integer.MAX_VALUE;
					this.pageTotal = 0;
					this.recordStart = 0;
				}
			}
		}else {
			this.pageID = 1;
			this.recordCount = 1;
			this.recordTotal = Integer.MAX_VALUE;
			this.pageTotal = 0;
			this.recordStart = 0;
		}
	}

	private Integer pageTotal = null;
	private Integer firstPage = null;
	private Integer lastPage = null;
	private Integer recordStart = null;
	private Integer beforePage = null;
	private Integer nextPage = null;
	
	public Integer getPageID() {
		return pageID;
	}
	public void setPageID(Integer pageID) {
		this.pageID = pageID;
	}
	public Integer getRecordCount() {
		return recordCount;
	}
	public void setRecordCount(Integer recordCount) {
		this.recordCount = recordCount;
	}
	public Integer getRecordTotal() {
		return recordTotal;
	}
	public void setRecordTotal(Integer recordTotal) {
		this.recordTotal = recordTotal;
	}
	public List<?> getListBean() {
		return listBean;
	}
	public void setListBean(List<?> list) {
		this.listBean = list;
	}
	
	
	public Integer getPageTotal() {
		return pageTotal;
	}
	public Integer getRecordStart() {
		return recordStart;
	}
	public Integer getLastPage() {
		if (recordTotal>0) {
			return this.getPageTotal();
		}else {
			return 0;
		}
	}
	public Integer getFirstPage() {
		if (recordTotal>0) {
			return 1;
		}else {
			return 0;
		}
	}
	public Integer getBeforePage() {
		if (pageID>1) {
			return pageID-1;
		}else {
			return 0;
		}
	}
	public Integer getNextPage() {
		if ((pageID+1)<(this.getPageTotal())) {
			return pageID+1;
		}else {
			return pageID;
		}
	}
	
	
}
