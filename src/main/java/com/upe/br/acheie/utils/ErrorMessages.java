package com.upe.br.acheie.utils;

public class ErrorMessages {

	public static final String MSG_ID_NULL = "The provided ID is null";
	public static final String MSG_UNEXPECTED_ERROR =
			"An error occurred while processing your data. Please try again!";;
	public static final String MSG_ELEMENT_NULL = "The provided element is null";
	public static final String MSG_AUTHENTICATION_ERROR =
			"There was a failure in user authentication. Please try again!";
	public static final String MSG_USER_EXISTS =
			"A user with the provided email is already registered";
	public static final String MSG_NOT_FOUND = "The requested element was not found";
	public static final String MSG_OPTIONAL_ERROR = "There is no value inside the Optional<?>";
	public static final String MSG_ALGORITHM_ERROR = "The provided algorithm is invalid";
	public static final String MSG_JWT_ERROR = "Error processing the JWT";
	public static final String MSG_SIGNATURE_ERROR = "The provided signature is invalid";
	public static final String MSG_TOKEN_EXPIRED = "The provided token has expired";
	public static final String MSG_MISSING_CLAIM = "The claim to be verified was not provided";
	public static final String MSG_CLAIM_ERROR = "The provided claim is invalid";
	public static final String MSG_FILTER_ENUM = "The provided filter value is invalid";
	public static final String MSG_NEW_PASSWORD_ERROR = "There was an error in the password change request. Please try again!";
	public static final String MSG_USER_NOT_FOUND = "User not found";
	public static final String MSG_ITEM_NOT_FOUND = "Item not found";
	public static final String MSG_POST_NOT_FOUND = "Post not found";
	public static final String MSG_USER_POST_NOT_FOUND = "This user does not have a post with this id";
	public static final String MSG_USER_COMMENT_NOT_FOUND = "This user does not have a comment for this post";
	public static final String MSG_INVALID_POST_CLOSURE = "A user cannot close a post that does not belong to them";
	public static final String MSG_INVALID_REGISTRATION_INFO = "Invalid registration information";
	public static final String MSG_EMAIL_REGISTERED = "This email is already registered";
	public static final String MSG_INVALID_EMAIL = "Please provide a valid email";
	public static final String MSG_INVALID_POST_TYPE_UPDATE = "It is not allowed to update the post type to RETURNED in this endpoint";
	public static final String MSG_INVALID_ITEM_STATE_UPDATE = "It is not allowed to update the item state to RETURNED in this endpoint";

	private ErrorMessages() {}
	}
