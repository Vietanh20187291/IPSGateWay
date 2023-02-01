function showConfirmModalDialog(cardId) {
    window.$('#cardId').text(cardId)
    window.$('#form-delete').attr('action', '/cards/'+cardId+'/delete')
    window.$('#form-delete').attr('method', 'post')
    jQuery.noConflict();
    var Modal = new bootstrap.Modal(document.getElementById('confirmationId'))
    Modal.show()

}