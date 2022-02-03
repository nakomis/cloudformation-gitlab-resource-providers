package com.cloudformation.gitlab.project;

import com.cloudformation.gitlab.core.GitLabProjectService;
import com.cloudformation.gitlab.core.GitLabServiceException;
import org.gitlab4j.api.GitLabApi;
import org.gitlab4j.api.models.Project;
import software.amazon.cloudformation.proxy.AmazonWebServicesClientProxy;
import software.amazon.cloudformation.proxy.HandlerErrorCode;
import software.amazon.cloudformation.proxy.Logger;
import software.amazon.cloudformation.proxy.ProgressEvent;
import software.amazon.cloudformation.proxy.OperationStatus;
import software.amazon.cloudformation.proxy.ResourceHandlerRequest;

import java.util.Objects;
import java.util.Optional;

public class DeleteHandler extends BaseHandlerStd {

    @Override
    public ProgressEvent<ResourceModel, CallbackContext> handleRequest(
        final AmazonWebServicesClientProxy proxy,
        final ResourceHandlerRequest<ResourceModel> request,
        final CallbackContext callbackContext,
        final Logger logger) {

        final ResourceModel model = request.getDesiredResourceState();

        GitLabProjectService gitLabService = initGitLabService(model.getServer(),model.getToken());
        try {
            gitLabService.delete(model.getId());
        } catch (GitLabServiceException e){
            logger.log("Error: " + e);
            return failure(model,HandlerErrorCode.InternalFailure);
        }
        return success(model);
    }
}
