import { render, screen } from '@testing-library/react';
import MenuAppBar from './MenuAppBar';

const mockUsedNavigate = jest.fn();
jest.mock('react-router-dom', () => ({
   ...jest.requireActual('react-router-dom'),
  useNavigate: () => mockUsedNavigate,
}));

test('renders learn react link', () => {
  render(<MenuAppBar />);
  const linkElement = screen.getByText(/Demo/i);
  expect(linkElement).toBeInTheDocument();
});
