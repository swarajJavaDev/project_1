import React, { useState, useEffect } from 'react';
import { useParams, useNavigate, Link } from 'react-router-dom';
import { postService, commentService } from '../services/api';
import LoadingSpinner from './LoadingSpinner';
import CommentSection from './CommentSection';

const PostDetail = () => {
  const { id } = useParams();
  const navigate = useNavigate();
  const [post, setPost] = useState(null);
  const [comments, setComments] = useState([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState('');

  useEffect(() => {
    fetchPostAndComments();
  }, [id]);

  const fetchPostAndComments = async () => {
    try {
      setLoading(true);
      setError('');
      
      // Fetch post details
      const postResponse = await postService.getPostById(id);
      setPost(postResponse.data);
      
      // Fetch comments for this post
      try {
        const commentsResponse = await commentService.getCommentsByPostId(id);
        setComments(commentsResponse.data || []);
      } catch (commentErr) {
        // Comments might not exist for this post, which is okay
        console.log('No comments found for this post');
        setComments([]);
      }
      
    } catch (err) {
      console.error('Error fetching post:', err);
      setError('Failed to fetch post details. Please check if the post exists.');
    } finally {
      setLoading(false);
    }
  };

  const handleDelete = async () => {
    if (window.confirm('Are you sure you want to delete this post?')) {
      try {
        await postService.deletePost(id);
        navigate('/');
      } catch (err) {
        console.error('Error deleting post:', err);
        setError('Failed to delete post');
      }
    }
  };

  const refreshComments = () => {
    fetchPostAndComments();
  };

  if (loading) return <LoadingSpinner />;

  if (error) {
    return (
      <div className="container">
        <div className="error-message">
          {error}
        </div>
        <Link to="/" className="btn btn-primary">
          Back to Posts
        </Link>
      </div>
    );
  }

  if (!post) {
    return (
      <div className="container">
        <div className="error-message">
          Post not found.
        </div>
        <Link to="/" className="btn btn-primary">
          Back to Posts
        </Link>
      </div>
    );
  }

  return (
    <div>
      <div className="back-btn">
        <Link to="/" className="btn btn-outline-secondary">
          ← Back to Posts
        </Link>
      </div>

      <article className="card">
        <div className="card-body">
          <h1 className="card-title">{post.title}</h1>
          <div className="post-meta mb-3">
            <small className="text-muted">Post ID: {post.id}</small>
          </div>
          
          <div className="mb-4">
            <h5>Description</h5>
            <p className="lead">{post.description}</p>
          </div>
          
          <div className="post-content">
            <h5>Content</h5>
            <p style={{ whiteSpace: 'pre-wrap' }}>{post.content}</p>
          </div>
          
          <div className="mt-4">
            <Link to={`/edit/${post.id}`} className="btn btn-primary me-2">
              Edit Post
            </Link>
            <button onClick={handleDelete} className="btn btn-danger">
              Delete Post
            </button>
          </div>
        </div>
      </article>

      {/* Comments Section */}
      <CommentSection
        postId={id}
        comments={comments}
        onCommentsUpdate={refreshComments}
      />
    </div>
  );
};

export default PostDetail;