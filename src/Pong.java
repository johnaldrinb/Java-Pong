import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.*;

public class Pong extends JFrame implements KeyListener, ActionListener{

	private int tick = 0;
	private int x_pos = 10;
	private int y_pos = 250;
	private int x_pos2 = 540;
	private int y_pos2 = 250;
	private int ball_x = 60;
	private int ball_y = 280;
	private int ballSpeed = 10;
	private int angle = 2;
	private int score1 = 0;
	private int score2 = 0;
	private int level = 0;

	private boolean elevate = false;
	private boolean toLeft = false;
	private boolean toUp = false;
	private boolean toUp1 = false;
	private boolean ballUp = false;
	private boolean ballMove = true;
	private boolean endGame = false;
	private boolean[] keysPressed = new boolean[256];
    private boolean[] keysReleased = new boolean[256];
	private boolean singlePlayer = true;
	private boolean noPlayer = true;
	private boolean hold = false;

	private JPanel border;
	private JPanel paddle;
	private JPanel paddle2;
	private JLabel lblScore1;
	private JLabel lblScore2;
	private JLabel lblTitle;
	private JLabel lblPaused;
	private JButton btn1p;
	private JButton btn2p;
	private JButton btnExit;
	private JButton btnLevel0;
	private JButton btnLevel1;
	private JButton btnBack;
	private JButton btnPause;
	private Ball ball;
	private Thread gameLoop;

	public Pong(){
		startGame();
	}

	private void startGame(){
		init();
		run();
	}

	private void init(){
		this.setSize(600, 550);
		this.setLayout(null);
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.getContentPane().setBackground(Color.BLACK);
		this.setVisible(true);
		this.setResizable(false);
		this.setTitle("PONG");
		this.addKeyListener(this);

		lblTitle = new JLabel("PONG", SwingConstants.CENTER);
		lblTitle.setBounds(10, 10, 560, 120);
		lblTitle.setFont(new Font("Courier New", 1, 72));
		//lblTitle.setBorder(BorderFactory.createLineBorder(Color.GREEN));
		lblTitle.setForeground(Color.GREEN);
		this.add(lblTitle);

		btn1p = new JButton("1 PLAYER");
		btn1p.setBounds(170, 150, 250, 80);
		btn1p.setBackground(Color.GREEN);
		btn1p.setForeground(Color.BLACK);
		btn1p.setFont(new Font("Courier New", 1, 35));
		btn1p.setFocusable(false);
		btn1p.addActionListener(this);
		this.add(btn1p);

		btn2p = new JButton("2 PLAYERS");
		btn2p.setBackground(Color.GREEN);
		btn2p.setForeground(Color.BLACK);
		btn2p.setFont(new Font("Courier New", 1, 35));
		btn2p.setBounds(170, 240, 250, 80);
		btn2p.setFocusable(false);
		btn2p.addActionListener(this);
		this.add(btn2p);

		btnExit = new JButton("EXIT");
		btnExit.setBackground(Color.GREEN);
		btnExit.setForeground(Color.BLACK);
		btnExit.setFont(new Font("Courier New", 1, 35));
		btnExit.setBounds(220, 330, 150, 80);
		btnExit.setFocusable(false);
		btnExit.addActionListener(this);
		this.add(btnExit);

		btnLevel0 = new JButton("EASY");
		btnLevel0.setBackground(Color.GREEN);
		btnLevel0.setForeground(Color.BLACK);
		btnLevel0.setFont(new Font("Courier New", 1, 35));
		btnLevel0.setBounds(220, 150, 150, 80);
		btnLevel0.setFocusable(false);
		btnLevel0.setVisible(false);
		btnLevel0.addActionListener(this);
		this.add(btnLevel0);

		btnLevel1 = new JButton("HARD");
		btnLevel1.setBackground(Color.GREEN);
		btnLevel1.setForeground(Color.BLACK);
		btnLevel1.setFont(new Font("Courier New", 1, 35));
		btnLevel1.setBounds(220, 240, 150, 80);
		btnLevel1.setFocusable(false);
		btnLevel1.setVisible(false);
		btnLevel1.addActionListener(this);
		this.add(btnLevel1);

		btnBack = new JButton("BACK");
		btnBack.setBackground(Color.GREEN);
		btnBack.setForeground(Color.BLACK);
		btnBack.setFont(new Font("Courier New", 1, 35));
		btnBack.setBounds(220, 330, 150, 80);
		btnBack.setFocusable(false);
		btnBack.setVisible(false);
		btnBack.addActionListener(this);
		this.add(btnBack);

		btnPause = new JButton("PAUSE");
		btnPause.setBackground(Color.GREEN);
		btnPause.setForeground(Color.BLACK);
		btnPause.setFont(new Font("Courier New", 1, 35));
		btnPause.setBounds(20, 460, 150, 50);
		btnPause.setFocusable(false);
		btnPause.setVisible(false);
		btnPause.addActionListener(this);
		this.add(btnPause);

		paddle = new JPanel();
		//paddle.setBackground(Color.decode("#00E676"));
		paddle.setBackground(Color.BLACK);
		paddle.setBorder(BorderFactory.createLineBorder(Color.GREEN, 3));
		paddle.setBounds(x_pos, y_pos, 30, 100);
		this.add(paddle);

		paddle2 = new JPanel();
		//paddle2.setBackground(Color.decode("#00E676"));
		paddle2.setBackground(Color.BLACK);
		paddle2.setBorder(BorderFactory.createLineBorder(Color.GREEN, 3));
		paddle2.setBounds(x_pos2, y_pos2, 30, 100);
		this.add(paddle2);

		ball = new Ball();
		ball.setBounds(paddle.getX() + 30, paddle.getY() + 30, 30, 30);
		this.add(ball);

		lblScore1 = new JLabel();
		lblScore1.setBounds(250, 470, 50, 50);
		lblScore1.setFont(new Font("Consolas", 1, 40));
		lblScore1.setText(Integer.toString(score1));
		lblScore1.setForeground(Color.GREEN);
		this.add(lblScore1);

		lblScore2 = new JLabel();
		lblScore2.setBounds(310, 470, 50, 50);
		lblScore2.setFont(new Font("Consolas", 1, 40));
		lblScore2.setText(Integer.toString(score2));
		lblScore2.setForeground(Color.GREEN);
		this.add(lblScore2);

		lblPaused = new JLabel("PAUSED", SwingConstants.CENTER);
		lblPaused.setBounds(170, 150, 250, 80);
		lblPaused.setForeground(Color.GREEN);
		lblPaused.setFont(new Font("Courier New", 1, 35));
		lblPaused.setVisible(false);
		this.add(lblPaused);

		border = new JPanel();
		border.setBounds(-10, 15, 700, 435);
		border.setBackground(Color.BLACK);
		border.setBorder(BorderFactory.createLineBorder(Color.GREEN, 2));
		this.add(border);
	}

	private void run(){
		endGame = false;
		score1 = 0;
		score2 = 0;
		lblScore1.setText(Integer.toString(score1));
		lblScore2.setText(Integer.toString(score2));

		paddle.setBounds(x_pos, y_pos, 30, 100);
		paddle2.setBounds(x_pos2, y_pos2, 30, 100);
		ball.setBounds(paddle.getX() + 30, paddle.getY() + 30, 30, 30);
		resetKeys();

		gameLoop = new Thread(new Runnable(){
			public void run(){
				endGame = false;
				score1 = 0;
				score2 = 0;
				lblScore1.setText(Integer.toString(score1));
				lblScore2.setText(Integer.toString(score2));

				paddle.setBounds(x_pos, y_pos, 30, 100);
				paddle2.setBounds(x_pos2, y_pos2, 30, 100);
				ball.setBounds(paddle.getX() + 30, paddle.getY() + 30, 30, 30);
				resetKeys();

				while(true){
					if(!endGame){
						if(tick % 30 == 0) getInput();				
						if(tick % ballSpeed == 0) updateSprites();					
						if(score1 == 7 || score2 == 7) endGame = true;
						tick++;
					}
					if(endGame){
						JOptionPane.showMessageDialog(null, "GAME OVER");
						break;
					}
					try{
						Thread.sleep(1);
					} catch(Exception e){}
				}

				int choice = JOptionPane.showConfirmDialog(null, "START NEW GAME?", "Pong", JOptionPane.YES_OPTION);
				if(choice == JOptionPane.YES_OPTION){
					run();
				}
				if(choice == JOptionPane.NO_OPTION){
					/*btn1p.setVisible(true);
					btn2p.setVisible(true);
					btnExit.setVisible(true);
					lblTitle.setVisible(true);
					ballMove = true;
					resetKeys();
					gameLoop.stop();*/
					dispose();
					new Pong();
				}
			}
		});
		gameLoop.start();
	}

	private void updateSprites(){
		if(!toLeft && ballMove){
			ball.setBounds(ball_x, ball_y, 30, 100);
			ball_x += 10;

			if(ballUp)
				ball_y -= angle;
			else if(!ballUp)
				ball_y += angle;
			
			if(isCollide(paddle2.getX() - 30, paddle2.getY(), 2))
				toLeft = true;
			else
				reset();
			
		} else if(toLeft && ballMove){
			ball.setBounds(ball_x, ball_y, 30, 100);
			ball_x -= 10;

			if(ballUp)
				ball_y -= angle;
			else if(!ballUp)
				ball_y += angle;

			if(isCollide(paddle.getX() + 30, paddle.getY(), 1))
				toLeft = false;
			else
				reset();
		}
		repaint();
	}

	private boolean isCollide(int x, int y, int pad){
		boolean result = false;

		if(ball_x == x){
			if(ball_y >= y && ball_y <= y + 100 || ball_y + 30 >= y && ball_y + 30 <= y + 100){
				if(ball_y == y + 30){
					angle = 2;
					ballSpeed = 30;
				} else if(ball_y > y + 30 && ball_y < y + 50){
					angle = 3;
					if(isKeyPressed(KeyEvent.VK_W) || isKeyPressed(KeyEvent.VK_S) || isKeyPressed(KeyEvent.VK_UP) || isKeyPressed(KeyEvent.VK_DOWN))
						ballSpeed = 25;
					else
						ballSpeed = 30;
				} else if(ball_y > y + 50){
					angle = 5;
					if(isKeyPressed(KeyEvent.VK_W) || isKeyPressed(KeyEvent.VK_S) || isKeyPressed(KeyEvent.VK_UP) || isKeyPressed(KeyEvent.VK_DOWN))
						ballSpeed = 20;
					else
						ballSpeed = 30;
				} else if(ball_y < y + 30 && ball_y > y + 10){
					angle = 3;
					if(isKeyPressed(KeyEvent.VK_W) || isKeyPressed(KeyEvent.VK_S) || isKeyPressed(KeyEvent.VK_UP) || isKeyPressed(KeyEvent.VK_DOWN))
						ballSpeed = 25;
					else
						ballSpeed = 30;
				} else if( ball_y < y + 10){
					angle = 5;
					if(isKeyPressed(KeyEvent.VK_W) || isKeyPressed(KeyEvent.VK_S) || isKeyPressed(KeyEvent.VK_UP) || isKeyPressed(KeyEvent.VK_DOWN))
						ballSpeed = 20;
					else
						ballSpeed = 30;
				}

				if(pad == 1){
					if(ballUp == toUp1){
						if(isKeyPressed(KeyEvent.VK_W) || isKeyPressed(KeyEvent.VK_S)){
							ballSpeed -= 5;
							angle -= 2;
						} else{
							ballSpeed -= 3;
						}
					}
					ballUp = toUp1;
				} else if(pad == 2){
					if(!singlePlayer){
						if(ballUp == toUp){
							if(isKeyPressed(KeyEvent.VK_UP) || isKeyPressed(KeyEvent.VK_DOWN)){
								ballSpeed -= 5;
								angle -= 2;
							} else{
								ballSpeed -= 3;
							}
						}
						ballUp = toUp;
					} else if(singlePlayer){
						if(ballUp == toUp)
							ballSpeed -= 3;
						ballUp = toUp;
					}
				}

				result = true;
			}
		} else {
			if(ball_y >= 420){
				ballUp = true;
			} else if(ball_y <= 20){
				ballUp = false;
			}
		}

		return result;
	}

	private void reset(){
		if(ball_x < 0){
			ballMove = false;
			toLeft = true;
			ball_x = paddle2.getX() - 30;
			ball_y = paddle2.getY() + 30;
			score2++;
			lblScore2.setText(Integer.toString(score2));
			ball.setBounds(ball_x, ball_y, 30, 30);
			ballSpeed = 30;
			hold = true;
			//JOptionPane.showMessageDialog(null, "PLAYER 2 SCORE!");
		} else if(ball_x > 560){
			ballMove = false;
			toLeft = false;
			ball_x = paddle.getX() + 30;
			ball_y = paddle.getY() + 30;
			score1++;
			lblScore1.setText(Integer.toString(score1));
			ball.setBounds(ball_x, ball_y, 30, 30);
			ballSpeed = 30;
			hold = true;
			//JOptionPane.showMessageDialog(null, "PLAYER 1 SCORE!");
		}
	}

	@Override
	public void actionPerformed(ActionEvent e){
		if(e.getSource() == btn1p){
			btn1p.setVisible(false);
			btn2p.setVisible(false);
			btnExit.setVisible(false);
			btnLevel0.setVisible(true);
			btnLevel1.setVisible(true);
			btnBack.setVisible(true);
			this.singlePlayer = true;

		} else if(e.getSource() == btn2p){
			btn1p.setVisible(false);
			btn2p.setVisible(false);
			btnExit.setVisible(false);
			this.singlePlayer = false;
			lblTitle.setVisible(false);
			gameLoop.stop();
			noPlayer = false;
			btnPause.setVisible(true);
			run();
		} else if(e.getSource() == btnExit){
			System.exit(0);
		} else if(e.getSource() == btnLevel0){
			btnLevel0.setVisible(false);
			btnLevel1.setVisible(false);
			btnBack.setVisible(false);
			lblTitle.setVisible(false);
			level = 0;
			gameLoop.stop();
			noPlayer = false;
			btnPause.setVisible(true);
			run();
		} else if(e.getSource() == btnLevel1){
			btnLevel0.setVisible(false);
			btnLevel1.setVisible(false);
			btnBack.setVisible(false);
			lblTitle.setVisible(false);
			level = 1;
			gameLoop.stop();
			noPlayer = false;
			btnPause.setVisible(true);
			run();
		} else if(e.getSource() == btnBack){
			btn1p.setVisible(true);
			btn2p.setVisible(true);
			btnExit.setVisible(true);
			btnLevel0.setVisible(false);
			btnLevel1.setVisible(false);
			btnBack.setVisible(false);
		} else if(e.getSource() == btnPause){
			ballMove = false;
			lblPaused.setVisible(true);
		}
	}

	@Override
	public void keyPressed(KeyEvent e){
		int keyId = (e.getKeyCode() & 255);
        keysPressed[keyId] = true;
        keysReleased[keyId] = false;
	}

	@Override
	public void keyReleased(KeyEvent e){
		int keyId = (e.getKeyCode() & 255);
        keysPressed[keyId] = false;
        keysReleased[keyId] = true;
	}

	@Override
	public void keyTyped(KeyEvent e){}


	public boolean isKeyPressed(int key){
        return keysPressed[key];
    }

    public boolean isKeyReleased(int key){
        return keysReleased[key];
    }

    public void resetKeys(){
        for (int i = 0; i < 256; i++){
            keysReleased[i] = false;
        }
    }

	private void getInput(){
		if(isKeyPressed(KeyEvent.VK_W)){
			if(y_pos >= 20) y_pos -= 10;
			if(ballMove == false){
				ballMove = true;
				lblPaused.setVisible(false);
			}
			paddle.setBounds(x_pos, y_pos, 30, 100);
			toUp1 = true;
		} else if(isKeyPressed(KeyEvent.VK_S)){
			if(y_pos <= 342) y_pos += 10;
			if(ballMove == false){
				ballMove = true;
				lblPaused.setVisible(false);
			}
			paddle.setBounds(x_pos, y_pos, 30, 100);
			toUp1 = false;
		}

		if(!singlePlayer){ // if not single player
			if(isKeyPressed(KeyEvent.VK_UP)){
				if(y_pos2 >= 23) y_pos2 -= 10;
				if(ballMove == false){
					ballMove = true;
					lblPaused.setVisible(false);
				}
				paddle2.setBounds(x_pos2, y_pos2, 30, 100);
				toUp = true;
			} else if(isKeyPressed(KeyEvent.VK_DOWN)){
				if(y_pos2 <= 340) y_pos2 += 10;
				if(ballMove == false){
					ballMove = true;
					lblPaused.setVisible(false);
				}
				paddle2.setBounds(x_pos2, y_pos2, 30, 100);
				toUp = false;
			}
		} else {	// if single player
			if(noPlayer){
				if(ball_y - 30 > y_pos && y_pos <= 340){
					paddle.setBounds(x_pos, y_pos += 5, 30, 100);
					toUp1 = false;
				} else if(ball_y - 30 < y_pos && y_pos >= 25){
					paddle.setBounds(x_pos, y_pos -= 5, 30, 100);
					toUp1 = true;
				}
			}

			if(level == 0){
				if(tick % 4 == 0){
					if(ball_y - 30 > y_pos2 && y_pos2 <= 340){
						paddle2.setBounds(x_pos2, y_pos2 += 10, 30, 100);
						toUp = false;
					} else if(ball_y - 30 < y_pos2 && y_pos2 >= 25){
						paddle2.setBounds(x_pos2, y_pos2 -= 10, 30, 100);
						toUp = true;
					}
				}
			} else if(level == 1){
				if(ball_y - 30 > y_pos2 && y_pos2 <= 340){
					if(!toUp1 && ball_x >= paddle2.getX() - 30){
						paddle2.setBounds(x_pos2, y_pos2 -= 10, 30, 100);
						toUp = true;
					} else if(toUp1 && ball_x >= paddle2.getX() - 30){
						paddle2.setBounds(x_pos2, y_pos2 += 10, 30, 100);
						toUp = false;
					} else if(ball_x < paddle2.getX() - 30){
						paddle2.setBounds(x_pos2, y_pos2 += 10, 30, 100);
						toUp = false;
					}
				} else if(ball_y - 30 < y_pos2 && y_pos2 >= 25){
					if(!toUp1 && ball_x >= paddle2.getX() - 30){
						paddle2.setBounds(x_pos2, y_pos2 -= 10, 30, 100);
						toUp = true;
					} else if(toUp1 && ball_x >= paddle2.getX() - 30){
						paddle2.setBounds(x_pos2, y_pos2 += 10, 30, 100);
						toUp = false;
					} else if(ball_x < paddle2.getX() - 30){
						paddle2.setBounds(x_pos2, y_pos2 -= 10, 30, 100);
						toUp = true;
					}
				}
			}
		}

		resetKeys();
	}

	/*class Ball extends JPanel{

		@Override
		public void paint(Graphics g){
			Graphics2D g2 = (Graphics2D)g;
			g2.setPaint(Color.GREEN);
			g2.fill(new Ellipse2D.Double(0, 0, 30, 30));
		}
	}*/

	public static void main(String[] args){
		Pong p = new Pong();
	}
}