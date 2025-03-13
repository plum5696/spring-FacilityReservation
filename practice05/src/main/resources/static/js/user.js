function fnUserDelete(idx){
	var isDelete = confirm("해당 사용자를 정말 삭제하시겠습니까?");
	if(isDelete==true){
		location.href='/user/userDelete.do/'+idx;
	}
}