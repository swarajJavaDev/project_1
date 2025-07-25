import axios from 'axios';

const API_BASE_URL = 'http://localhost:8080/api';

// Create axios instance with default config
const api = axios.create({
  baseURL: API_BASE_URL,
  headers: {
    'Content-Type': 'application/json',
  },
});

// Post API services
export const postService = {
  // Get all posts with pagination
  getAllPosts: (pageNo = 0, pageSize = 5, sortBy = 'id', sortDir = 'asc') => {
    return api.get(`/post?pageNo=${pageNo}&pageSize=${pageSize}&sortBy=${sortBy}&sortDir=${sortDir}`);
  },

  // Get single post by ID
  getPostById: (id) => {
    return api.get(`/post/${id}`);
  },

  // Create new post
  createPost: (postData) => {
    return api.post('/post', postData);
  },

  // Update existing post
  updatePost: (id, postData) => {
    return api.put(`/post/${id}`, postData);
  },

  // Delete post
  deletePost: (id) => {
    return api.delete(`/post/${id}`);
  },
};

// Comment API services
export const commentService = {
  // Get comments for a specific post
  getCommentsByPostId: (postId) => {
    return api.get(`/posts/${postId}/comments`);
  },

  // Create new comment
  createComment: (postId, commentData) => {
    return api.post(`/posts/${postId}/comments`, commentData);
  },

  // Update comment
  updateComment: (postId, commentId, commentData) => {
    return api.put(`/posts/${postId}/comments/${commentId}`, commentData);
  },

  // Delete comment
  deleteComment: (postId, commentId) => {
    return api.delete(`/posts/${postId}/comments/${commentId}`);
  },
};

// Add request interceptor for logging (optional)
api.interceptors.request.use(
  (config) => {
    console.log(`Making ${config.method.toUpperCase()} request to: ${config.url}`);
    return config;
  },
  (error) => {
    return Promise.reject(error);
  }
);

// Add response interceptor for error handling
api.interceptors.response.use(
  (response) => {
    return response;
  },
  (error) => {
    if (error.response) {
      // Server responded with error status
      console.error('API Error:', error.response.data);
    } else if (error.request) {
      // Request was made but no response received
      console.error('Network Error:', error.message);
    } else {
      // Something else happened
      console.error('Error:', error.message);
    }
    return Promise.reject(error);
  }
);

export default api;