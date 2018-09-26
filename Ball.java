//package pong;

import java.awt.Color;
import java.awt.Graphics;
import java.util.Random;

public class Ball
{
	private static Random ran = new Random();
	private static int color = ran.nextInt(0xffffff);
	public int x, y, width = 25, height = 25;

	public int motionX, motionY;

	public Random random;

	private Pong pong;

	public int amountOfHits;

	public Ball(Pong pong)
	{
		this.pong = pong;

		this.random = new Random();

		spawn();
	}

	public void update(Paddle paddle1, Paddle paddle2)
	{
		int speed = 5;

		this.x += motionX * speed;
		this.y += motionY * speed;
		//Collision with ceilings
		//If ball is bouncing off the ceiling reset it to respective  bounds
			//Ball below lower ceiling || Ball above upper ceiling
		if (this.y + height - motionY > pong.height || this.y + motionY < 0)
		{
			
			if (this.motionY < 0)
			{
				this.y = 0;
				//Reflect below(towards the lower cceiling)
				this.motionY = random.nextInt(4);

				if (motionY == 0)
				{
					motionY = 1;
				}
			}
			
			else
			{
				//Reflect up(tOWARDS THE UPPER ceiling)
				this.motionY = -random.nextInt(4);
				this.y = pong.height - height;

				if (motionY == 0)
				{
					motionY = -1;
				}
			}
		}

		//Collision with walls and paddles
		
		//Collision with paddle 1
		if (checkCollision(paddle1) == 1)
		{
			this.motionX = 1 + (amountOfHits / 5);//Positive direction
			this.motionY = -2 + random.nextInt(4); //Random

			if (motionY == 0)
			{
				motionY = 1;
			}

			amountOfHits++;
		}
		//Collision with paddle 2
		else if (checkCollision(paddle2) == 1)
		{
			this.motionX = -1 - (amountOfHits / 5);//Negative direction
			this.motionY = -2 + random.nextInt(4); //Random

			if (motionY == 0)
			{
				motionY = 1;
			}

			amountOfHits++;
		}

		
		
		//If it collides with wall
		if (checkCollision(paddle1) == 2)
		{
			paddle2.score++;
			spawn();
		}
		else if (checkCollision(paddle2) == 2)
		{
			paddle1.score++;
			spawn();
		}
	}

	public void spawn()
	{
		this.amountOfHits = 0;
		this.x = pong.width / 2 - this.width / 2;
		this.y = pong.height / 2 - this.height / 2;
		//Random direcction speed along Y
		this.motionY = -2 + random.nextInt(4);
		//Any speed cannot be zero thus checking and correcting it
		if (motionY == 0)
		{
			motionY = 1;
		}
		//Random x-direction
		if (random.nextBoolean())
		{
			motionX = 1;
		}
		else
		{
			motionX = -1;
		}
	}

	public int checkCollision(Paddle paddle)
	{
		if (this.x < paddle.x + paddle.width && this.x + width > paddle.x && this.y < paddle.y + paddle.height && this.y + height > paddle.y)
		{
			color = ran.nextInt(0xffffff);
			return 1; //bounce
		}
		else if ((paddle.x > x && paddle.paddleNumber == 1) || (paddle.x < x - width && paddle.paddleNumber == 2))
		{
			return 2; //score
		}

		return 0; //nothing
	}

	public void render(Graphics g)
	{/*
		Color c = new Color(color);
		g.setColor(c);
		g.fillOval(x, y, width, height);*/
		g.drawImage(Pong.ball_img, x, y, null);
	}

}
