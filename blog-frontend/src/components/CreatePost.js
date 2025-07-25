import React, { useState } from 'react';
import { useNavigate, Link } from 'react-router-dom';
import { postService } from '../services/api';

const CreatePost = () => {
  const navigate = useNavigate();
  const [formData, setFormData] = useState({
    title: '',
    description: '',
    content: ''
  });
  const [loading, setLoading] = useState(false);
  const [errors, setErrors] = useState([]);
  const [successMessage, setSuccessMessage] = useState('');

  const handleInputChange = (e) => {
    setFormData({
      ...formData,
      [e.target.name]: e.target.value
    });
    // Clear errors when user starts typing
    if (errors.length > 0) {
      setErrors([]);
    }
  };

  const validateForm = () => {
    const newErrors = [];
    
    if (formData.title.trim().length < 2) {
      newErrors.push('Title should be at least 2 characters');
    }
    
    if (formData.description.trim().length < 5) {
      newErrors.push('Description should be at least 5 characters');
    }
    
    if (formData.content.trim().length < 15) {
      newErrors.push('Content should be at least 15 characters');
    }
    
    return newErrors;
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    
    const validationErrors = validateForm();
    if (validationErrors.length > 0) {
      setErrors(validationErrors);
      return;
    }
    
    setLoading(true);
    setErrors([]);
    setSuccessMessage('');

    try {
      const response = await postService.createPost(formData);
      setSuccessMessage('Post created successfully!');
      
      // Redirect to the new post after a short delay
      setTimeout(() => {
        navigate(`/posts/${response.data.id}`);
      }, 1500);
      
    } catch (err) {
      console.error('Error creating post:', err);
      
      if (err.response && err.response.data) {
        // Handle validation errors from backend
        if (Array.isArray(err.response.data)) {
          setErrors(err.response.data);
        } else if (typeof err.response.data === 'string') {
          setErrors([err.response.data]);
        } else {
          setErrors(['Failed to create post. Please check your input.']);
        }
      } else {
        setErrors(['Failed to create post. Please make sure the backend server is running.']);
      }
    } finally {
      setLoading(false);
    }
  };

  return (
    <div className="form-container">
      <div className="back-btn">
        <Link to="/" className="btn btn-outline-secondary">
          ← Back to Posts
        </Link>
      </div>

      <div className="card">
        <div className="card-header">
          <h2>Create New Post</h2>
        </div>
        <div className="card-body">
          {successMessage && (
            <div className="success-message">
              {successMessage}
            </div>
          )}

          {errors.length > 0 && (
            <div className="error-message">
              <ul className="mb-0">
                {errors.map((error, index) => (
                  <li key={index}>{error}</li>
                ))}
              </ul>
            </div>
          )}

          <form onSubmit={handleSubmit}>
            <div className="mb-3">
              <label htmlFor="title" className="form-label">Title *</label>
              <input
                type="text"
                className="form-control"
                id="title"
                name="title"
                value={formData.title}
                onChange={handleInputChange}
                placeholder="Enter post title (minimum 2 characters)"
                required
              />
            </div>

            <div className="mb-3">
              <label htmlFor="description" className="form-label">Description *</label>
              <textarea
                className="form-control"
                id="description"
                name="description"
                rows="3"
                value={formData.description}
                onChange={handleInputChange}
                placeholder="Enter post description (minimum 5 characters)"
                required
              ></textarea>
            </div>

            <div className="mb-3">
              <label htmlFor="content" className="form-label">Content *</label>
              <textarea
                className="form-control"
                id="content"
                name="content"
                rows="8"
                value={formData.content}
                onChange={handleInputChange}
                placeholder="Enter post content (minimum 15 characters)"
                required
              ></textarea>
            </div>

            <div className="d-flex gap-2">
              <button 
                type="submit" 
                className="btn btn-primary"
                disabled={loading}
              >
                {loading ? 'Creating...' : 'Create Post'}
              </button>
              <Link to="/" className="btn btn-secondary">
                Cancel
              </Link>
            </div>
          </form>
        </div>
      </div>
    </div>
  );
};

export default CreatePost;