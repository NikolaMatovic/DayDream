import { render, screen } from '@testing-library/react';
import App from './App';

test('renders daydream heading', () => {
  render(<App />);
  const headingElement = screen.getByRole('heading', { name: 'DayDream' });
  expect(headingElement).toBeInTheDocument();
});
