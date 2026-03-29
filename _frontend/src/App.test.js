import { render, screen } from '@testing-library/react';
import App from './App';

test('renders login or daydream app', () => {
  render(<App />);
  // Either Login or DayDream heading should be visible
  const loginHeading = screen.queryByText(/DayDream Login/);
  const appHeading = screen.queryByRole('heading', { name: /DayDream/ });
  
  expect(loginHeading || appHeading).toBeTruthy();
});
