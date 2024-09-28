$(document).ready(function() {
    console.log( "DOCUMENT READY!" );
	
	initEvents();
	
});

function initEvents() {
	console.log( "initEvents()" );
	
	$(document).on('change', '#member_authority select', function() {
		console.log( "CHANGE!!" );
		
		let mId = $(this).data('m_id');
		let mNo = $(this).data('m_no');
		let currentAuthorityNo = $(this).data('m_authority_no');
		let targetAuthorityNo = $(this).val();
		
		ajaxUpdateMemberAuthority(mId, mNo, currentAuthorityNo, targetAuthorityNo);
		
	});
	
}

function ajaxUpdateMemberAuthority(mId, mNo, currentAuthorityNo, targetAuthorityNo) {
	console.log( "ajaxUpdateMemberAuthority()" );
	
	let result = confirm('정말 권한을 업데이트 하시겠습니까?');
	if (result) {
		
		let msg = {
			'mNo': mNo,
			'targetAuthorityNo' : targetAuthorityNo,
		}
		
		$.ajax({
			url: '/admin/updateMemberAuthority',
			method: 'PUT',
			data: JSON.stringify(msg),
			contentType: 'application/json; charset=utf-8',
			dataType: 'json',
			success: function(data) {
				console.log( "ajaxUpdateMemberAuthority() COMMUNICATION SUCCESS!!" );
				
				console.log('data.result: ', data.result); 
				
			},
			error: function() {
				console.log( "ajaxUpdateMemberAuthority() COMMUNICATION ERROR!!" );
				
				alert('권한 업데이트에 실패했습니다.');
				
			}
		});
		
		
	} else {
		alert('권한 업데이트를 취소했습니다.');
		
		$(`#${mId}`).val(currentAuthorityNo).prop('selected', true);
		
	}
	
}