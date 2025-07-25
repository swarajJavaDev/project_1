import React, { useState } from 'react';
import { commentService } from '../services/api';

const CommentSection = ({ postId, comments, onCommentsUpdate }) => {
  const [showForm, setShowForm] = useState(false);
  const [formData, setFormData] = useState({
    name: '',
    email: '',
    body: ''
  });
  const [loading, setLoading] = useState(false);
  const [error, setError] = useState('');
  const [editingComment, setEditingComment] = useState(null);

  const handleInputChange = (e) => {
    setFormData({
      ...formData,
      [e.target.name]: e.target.value
    });
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    setLoading(true);
    setError('');

    try {
      if (editingComment) {
        await commentService.updateComment(postId, editingComment.id, formData);
        setEditingComment(null);
      } else {
        await commentService.createComment(postId, formData);
      }
      
      setFormData({ name: '', email: '', body: '' });
      setShowForm(false);
      onCommentsUpdate();
    } catch (err) {
      console.error('Error saving comment:', err);
      setError(editingComment ? 'Failed to update comment' : 'Failed to create comment');
    } finally {
      setLoading(false);
    }
  };

  const handleEdit = (comment) => {
    setEditingComment(comment);
    setFormData({
      name: comment.name,
      email: comment.email,
      body: comment.body
    });
    setShowForm(true);
  };

  const handleDelete = async (commentId) => {
    if (window.confirm('Are you sure you want to delete this comment?')) {
      try {
        await commentService.deleteComment(postId, commentId);
        onCommentsUpdate();
      } catch (err) {
        console.error('Error deleting comment:', err);
        setError('Failed to delete comment');
      }
    }
  };

  const cancelEdit = () => {
    setEditingComment(null);
    setFormData({ name: '', email: '', body: '' });
    setShowForm(false);
    setError('');
  };

  return (
    <div className="comment-section">
      <div className="d-flex justify-content-between align-items-center mb-3">
        <h4>Comments ({comments.length})</h4>
        {!showForm && (
          <button 
            className="btn btn-primary"
            onClick={() => setShowForm(true)}
          >
            Add Comment
          </button>
        )}
      </div>

      {error && (
        <div className="error-message">
          {error}
        </div>
      )}

      {/* Comment Form */}
      {showForm && (
        <div className="card mb-4">
          <div className="card-header">
            <h5>{editingComment ? 'Edit Comment' : 'Add New Comment'}</h5>
          </div>
          <div className="card-body">
            <form onSubmit={handleSubmit}>
              <div className="mb-3">
                <label htmlFor="name" className="form-label">Name *</label>
                <input
                  type="text"
                  className="form-control"
                  id="name"
                  name="name"
                  value={formData.name}
                  onChange={handleInputChange}
                  required
                />
              </div>
              
              <div className="mb-3">
                <label htmlFor="email" className="form-label">Email *</label>
                <input
                  type="email"
                  className="form-control"
                  id="email"
                  name="email"
                  value={formData.email}
                  onChange={handleInputChange}
                  required
                />
              </div>
              
              <div className="mb-3">
                <label htmlFor="body" className="form-label">Comment *</label>
                <textarea
                  className="form-control"
                  id="body"
                  name="body"
                  rows="3"
                  value={formData.body}
                  onChange={handleInputChange}
                  required
                ></textarea>
              </div>
              
              <div className="d-flex gap-2">
                <button 
                  type="submit" 
                  className="btn btn-primary"
                  disabled={loading}
                >
                  {loading ? 'Saving...' : (editingComment ? 'Update Comment' : 'Post Comment')}
                </button>
                <button 
                  type="button" 
                  className="btn btn-secondary"
                  onClick={cancelEdit}
                  disabled={loading}
                >
                  Cancel
                </button>
              </div>
            </form>
          </div>
        </div>
      )}

      {/* Comments List */}
      {comments.length === 0 ? (
        <p className="text-muted">No comments yet. Be the first to comment!</p>
      ) : (
        <div className="comments-list">
          {comments.map((comment) => (
            <div key={comment.id} className="card comment-card">
              <div className="card-body">
                <div className="d-flex justify-content-between align-items-start mb-2">
                  <div>
                    <strong>{comment.name}</strong>
                    <small className="text-muted ms-2">{comment.email}</small>
                  </div>
                  <div className="btn-group btn-group-sm">
                    <button
                      className="btn btn-outline-primary"
                      onClick={() => handleEdit(comment)}
                    >
                      Edit
                    </button>
                    <button
                      className="btn btn-outline-danger"
                      onClick={() => handleDelete(comment.id)}
                    >
                      Delete
                    </button>
                  </div>
                </div>
                <p className="mb-0" style={{ whiteSpace: 'pre-wrap' }}>
                  {comment.body}
                </p>
              </div>
            </div>
          ))}
        </div>
      )}
    </div>
  );
};

export default CommentSection;