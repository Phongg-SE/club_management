<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!-- Import iziToast -->
<link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/iziToast.min.css">
<script src="${pageContext.request.contextPath}/assets/js/iziToast.min.js"></script>
<!-- Modal -->
<div class="modal fade" id="addSettingModal" tabindex="-1" role="dialog" aria-labelledby="addModalLabel" aria-hidden="true">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title" id="addModalLabel">Add Setting</h5>
                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                    <span aria-hidden="true">&times;</span>
                </button>
            </div>
            <div class="modal-body">
                <form id="addSettingForm" action="addSetting" method="POST">

                    <!--Type-->
                    <div class="form-group">
                        <label for="type_id">Type <span class="text-danger">*</span></label>
                        <select class="form-select" id="typeAddInput" name="type_id" required>
                            <option value="1">Role</option>
                            <option value="2">Club Category</option>
                            <!--                            <option value="3">Club Member</option>
                                                        <option value="4">User</option>-->
                        </select>
                        <div id="typeAddError" class="error text-danger"></div>
                    </div>
                    <!--Name-->
                    <div class="form-group">
                        <label for="name">Name <span class="text-danger">*</span></label>
                        <input type="text" class="form-control" id="nameEditInput" name="name" required>
                        <div id="nameAddError" class="error text-danger"></div>
                    </div>

                    <!--value-->
                    <div class="form-group">
                        <label for="value">Value <span class="text-danger">*</span></label>
                        <input type="text" class="form-control" id="valueEditInput" name="value" required>
                        <div id="valueAddError" class="error text-danger"></div>
                    </div>
                    <!--priority-->
                    <div class="form-group">
                        <label for="priority">Priority <span class="text-danger">*</span></label>
                        <input type="number" class="form-control" id="priorityEditInput" name="priority"
                               min="1" max="10" step="1" value="1"
                               onkeydown="return false;"
                               oninput="this.value = this.value.replace(/[^0-9]/g, '').slice(0, 2);"
                               onblur="if (this.value === '' || this.value < 1)
                       this.value = 1;">
                        <div id="priorityEditError" class="error text-danger"></div>
                    </div>
                    <!--status-->
                    <div class="form-group">
                        <label for="status">Status</label>
                        <select class="form-select" id="statusEditInput" name="status">
                            <option value="Active">Active</option>
                            <option value="Inactive">Inactive</option>
                        </select>
                        <div id="statusEditError" class="error text-danger"></div>
                    </div>
                    <!--description-->
                    <div class="form-group">
                        <label for="description">Description <span class="text-danger">*</span></label>
                        <textarea class="form-control" id="descriptionEditInput"
                                  name="description" rows="4" required></textarea>
                        <div id="descriptionAddError" class="error text-danger"></div>
                    </div>

                </form>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-secondary" data-dismiss="modal">Cancel</button>
                <button type="submit" class="btn btn-primary" form="addSettingForm" onclick="validateForm()">Add</button>
            </div>
        </div>
    </div>
</div>
