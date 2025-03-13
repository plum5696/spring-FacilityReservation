function fnDeleteFacility(idx){
	var isDelete=confirm("해당 시설을 정말 삭제하시겠습니까?");
	if(isDelete){
		location.href="/facility/delete/"+idx;
	}
}