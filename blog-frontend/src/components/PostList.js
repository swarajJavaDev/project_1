import React, { useState, useEffect } from 'react';
import { Link } from 'react-router-dom';
import { postService } from '../services/api';
import LoadingSpinner from './LoadingSpinner';

const PostList = () => {
  const [posts, setPosts] = useState([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState('');
  const [currentPage, setCurrentPage] = useState(0);
  const [totalPages, setTotalPages] = useState(0);
  const [pageSize] = useState(5);
  const [sortBy, setSortBy] = useState('id');
  const [sortDir, setSortDir] = useState('desc');

  useEffect(() => {
    fetchPosts();
  }, [currentPage, sortBy, sortDir]);

  const fetchPosts = async () => {
    try {
      setLoading(true);
      setError('');
      const response = await postService.getAllPosts(currentPage, pageSize, sortBy, sortDir);
      
      if (response.data && response.data.postDto) {
        setPosts(response.data.postDto);
        setTotalPages(response.data.totalPages);
      } else {
        setError('Invalid response format from server');
      }
    } catch (err) {
      console.error('Error fetching posts:', err);
      setError('Failed to fetch posts. Please make sure the backend server is running.');
    } finally {
      setLoading(false);
    }
  };

  const handleDelete = async (postId) => {
    if (window.confirm('Are you sure you want to delete this post?')) {
      try {
        await postService.deletePost(postId);
        fetchPosts(); // Refresh the list
      } catch (err) {
        console.error('Error deleting post:', err);
        setError('Failed to delete post');
      }
    }
  };

  const handlePageChange = (page) => {
    setCurrentPage(page);
  };

  const handleSortChange = (newSortBy) => {
    if (sortBy === newSortBy) {
      setSortDir(sortDir === 'asc' ? 'desc' : 'asc');
    } else {
      setSortBy(newSortBy);
      setSortDir('asc');
    }
  };

  const truncateContent = (content, length = 150) => {
    if (content.length <= length) return content;
    return content.substring(0, length) + '...';
  };

  if (loading) return <LoadingSpinner />;

  return (
    <div>
      <div className="d-flex justify-content-between align-items-center mb-4">
        <h1>All Blog Posts</h1>
        <Link to="/create" className="btn btn-primary">
          Create New Post
        </Link>
      </div>

      {error && (
        <div className="error-message">
          {error}
        </div>
      )}

      {/* Sorting Controls */}
      <div className="mb-3">
        <small className="text-muted">Sort by:</small>
        <div className="btn-group ms-2" role="group">
          <button
            className={`btn btn-sm ${sortBy === 'id' ? 'btn-primary' : 'btn-outline-primary'}`}
            onClick={() => handleSortChange('id')}
          >
            Date {sortBy === 'id' && (sortDir === 'asc' ? '↑' : '↓')}
          </button>
          <button
            className={`btn btn-sm ${sortBy === 'title' ? 'btn-primary' : 'btn-outline-primary'}`}
            onClick={() => handleSortChange('title')}
          >
            Title {sortBy === 'title' && (sortDir === 'asc' ? '↑' : '↓')}
          </button>
        </div>
      </div>

      {posts.length === 0 ? (
        <div className="text-center">
          <p>No posts found. <Link to="/create">Create your first post!</Link></p>
        </div>
      ) : (
        <>
          {posts.map((post) => (
            <div key={post.id} className="card post-card">
              <div className="card-body">
                <h5 className="card-title">{post.title}</h5>
                <p className="card-text">{truncateContent(post.description)}</p>
                <p className="card-text">
                  <small className="text-muted">
                    Content: {truncateContent(post.content, 100)}
                  </small>
                </p>
                <div className="d-flex justify-content-between align-items-center">
                  <div>
                    <Link to={`/posts/${post.id}`} className="btn btn-primary btn-sm me-2">
                      Read More
                    </Link>
                    <Link to={`/edit/${post.id}`} className="btn btn-outline-secondary btn-sm me-2">
                      Edit
                    </Link>
                    <button
                      onClick={() => handleDelete(post.id)}
                      className="btn btn-outline-danger btn-sm"
                    >
                      Delete
                    </button>
                  </div>
                  <small className="text-muted">Post ID: {post.id}</small>
                </div>
              </div>
            </div>
          ))}

          {/* Pagination */}
          {totalPages > 1 && (
            <nav className="pagination-container">
              <ul className="pagination">
                <li className={`page-item ${currentPage === 0 ? 'disabled' : ''}`}>
                  <button
                    className="page-link"
                    onClick={() => handlePageChange(currentPage - 1)}
                    disabled={currentPage === 0}
                  >
                    Previous
                  </button>
                </li>
                {[...Array(totalPages)].map((_, index) => (
                  <li key={index} className={`page-item ${currentPage === index ? 'active' : ''}`}>
                    <button
                      className="page-link"
                      onClick={() => handlePageChange(index)}
                    >
                      {index + 1}
                    </button>
                  </li>
                ))}
                <li className={`page-item ${currentPage === totalPages - 1 ? 'disabled' : ''}`}>
                  <button
                    className="page-link"
                    onClick={() => handlePageChange(currentPage + 1)}
                    disabled={currentPage === totalPages - 1}
                  >
                    Next
                  </button>
                </li>
              </ul>
            </nav>
          )}
        </>
      )}
    </div>
  );
};

export default PostList;