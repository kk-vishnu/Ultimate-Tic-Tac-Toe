import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.IOException;

class ultimate_tic_tac_toe implements ActionListener
{
	static int player=1;
	int blk=-1;
	JFrame jfm=new JFrame("TIC-TAC-TOE(test)");
	JLabel jlb;
	JButton jb[][],play,rules;
	JPanel finaldisp=new JPanel(new GridLayout(4,3,5,5));
	JPanel todisp[]=new JPanel[9];
	int blockStatus[]=new int[9];
	char blockValue[]=new char[9];
	ultimate_tic_tac_toe()
	{
		jfm.setLayout(new FlowLayout());
		jfm.setSize(1000,1000);
		jfm.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);	
		jlb=new JLabel("  Press play to start");
		play=new JButton("PLAY");
		rules=new JButton("RULES");
		jb=new JButton[9][9];

		for(int i=0;i<9;i++)
		{
			todisp[i]=new JPanel(new GridLayout(3,3));
			for(int j=0;j<9;j++)
			{
				jb[i][j]=new JButton(" ");
				jb[i][j].addActionListener(this);
				jb[i][j].setPreferredSize(new Dimension(50,50));
				todisp[i].add(jb[i][j]);
			}
			finaldisp.add(todisp[i]);
		}
		for(int i=0;i<9;i++)
			for(int j=0;j<9;j++)
			{
				jb[i][j].setBackground(Color.black);
				jb[i][j].setEnabled(false);
			}

		play.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent ae)
			{
				for(int i=0;i<9;i++)
				{
					blockStatus[i]=0;
					blockValue[i]=' ';
					for(int j=0;j<9;j++)
					{
						jb[i][j].setText(" ");
						jb[i][j].setEnabled(true);
						jb[i][j].setBackground(Color.white);
					}
				}
				player=1;
				jlb.setText("   Player 1's turn[X]");
			}
		});
		
		rules.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent ae)
			{
				try
				{
					Desktop desktop=null;
      				if(Desktop.isDesktopSupported())
      				{desktop = Desktop.getDesktop();}
      				desktop.open(new File("Rules.pdf"));
    			} 
    			catch (IOException ioe){}
			}
		});

		finaldisp.add(play);
		finaldisp.add(jlb);
		finaldisp.add(rules);
		jfm.add(finaldisp);
		jfm.setVisible(true);
	}
	public void actionPerformed(ActionEvent ae)
	{
		play.setText("NEW GAME");
		for(int i=0;i<9;i++)
		{
			for(int j=0;j<9;j++)
			{
				if(ae.getSource()==jb[i][j])
				{
					blk=j;
					if(player==1)
					{
						jb[i][j].setText("X");
						player=2;
						jlb.setText("   Player 2's turn[O]");
					}
					else
					{
						jb[i][j].setText("O");
						player=1;
						jlb.setText("   Player 1's turn[X]");
					}
					jb[i][j].setEnabled(false);
					for(int k=0;k<9;k++)
						for(int l=0;l<9;l++)
						{
							if(blockStatus[k]!=1)
								jb[k][l].setBackground(Color.white);
						}
				}
			}
		}
		for(int i=0;i<9;i++)	
		{
			if(blockStatus[i]==1)
				continue;
			else if(isBlockDone(i)==0)
			{
				blockStatus[i]=1;
				for(int j=0;j<9;j++)
					jb[i][j].setEnabled(false);
				if(player==1)
				{
					for(int j=0;j<9;j++)
					{
						jb[i][j].setText("O");
						jb[i][j].setBackground(Color.blue);
					}
					blockValue[i]='O';
				}
				else
				{
					for(int j=0;j<9;j++)
					{
						jb[i][j].setText("X");
						jb[i][j].setBackground(Color.red);
					}
					blockValue[i]='X';
				}
			}
			else if(isBlockDone(i)==2)
			{
				blockStatus[i]=1;
				for(int j=0;j<9;j++)
				{
					jb[i][j].setText("D");
					jb[i][j].setBackground(Color.darkGray);
				}
				blockValue[i]='D';
			}
			setNextBlock(blk);
		}

		if(isGameDone()==0)
		{
			for(int i=0;i<9;i++)
			{
				for(int j=0;j<9;j++)
				{
					jb[i][j].setEnabled(false);
					if(player==1)
					{
						jb[i][j].setText("O");
						jb[i][j].setBackground(Color.blue);
					}
					else
					{
						jb[i][j].setText("X");
						jb[i][j].setBackground(Color.red);
					}
				}
			}
			if(player==1)
				jlb.setText("  Player 2[O] wins!");
			else
				jlb.setText("  Player 1[X] wins!");
		}
		else if(isGameDone()==2)
		{
			for(int i=0;i<9;i++)
				for(int j=0;j<9;j++)
				{
					jb[i][j].setEnabled(false);
					jb[i][j].setText("D");
					jb[i][j].setBackground(Color.darkGray);
				}
			jlb.setText("       DRAW MATCH!");
		}
	}

	void setNextBlock(int z)
	{
		for(int i=0;i<9;i++)
			for(int j=0;j<9;j++)
				jb[i][j].setEnabled(false);
		if(blockStatus[z]==1)
		{
			for(int i=0;i<9;i++)
			{
				if(blockStatus[i]==1)
					continue;
				for(int j=0;j<9;j++)
				{
					if((jb[i][j].getText()).charAt(0)==' ')
					{
						jb[i][j].setEnabled(true);
						jb[i][j].setBackground(Color.lightGray);
					}
				}
			}
		}

		else
		{
			for(int j=0;j<9;j++)
				if((jb[z][j].getText()).charAt(0)==' ')
				{
					jb[z][j].setEnabled(true);
					jb[z][j].setBackground(Color.lightGray);
				}
		}
	}

	int isBlockDone(int k)
	{
		char arr[][]=new char[3][3];
		for(int i=0;i<3;i++)
			for(int j=0;j<3;j++)
				arr[i][j]=(jb[k][(3*i)+j].getText()).charAt(0);
		
		if( arr[0][0]==arr[0][1] && arr[0][1]==arr[0][2] && arr[0][0]!=' ')
 		return 0;
 		else if( arr[1][0]==arr[1][1] && arr[1][1]==arr[1][2] && arr[1][0]!=' ')
 		return 0;
 		else if( arr[2][0]==arr[2][1] && arr[2][1]==arr[2][2] && arr[2][0]!=' ')
 		return 0;
 		else if( arr[0][0]==arr[1][0] && arr[1][0]==arr[2][0] && arr[0][0]!=' ')
 		return 0;
 		else if( arr[0][1]==arr[1][1] && arr[1][1]==arr[2][1] && arr[0][1]!=' ')
 		return 0;
 		else if( arr[0][2]==arr[1][2] && arr[1][2]==arr[2][2] && arr[0][2]!=' ')
 		return 0;
 		else if( arr[0][0]==arr[1][1] && arr[1][1]==arr[2][2] && arr[0][0]!=' ')
 		return 0;
 		else if( arr[0][2]==arr[1][1] && arr[1][1]==arr[2][0] && arr[0][2]!=' ')
 		return 0;

 		else if ( (arr[0][0]!=' ') && (arr[0][1]!=' ') && (arr[0][2]!=' ') && (arr[1][0]!=' ') && (arr[1][1]!=' ') && (arr[1][2]!=' ') && (arr[2][0]!=' ') &&(arr[2][1]!=' ') &&(arr[2][2]!=' ') )
		return 2;

		return 1;
	}

	int isGameDone()
	{
		char arr[][]=new char[3][3];
		for(int i=0;i<3;i++)
			for(int j=0;j<3;j++)
				arr[i][j]=blockValue[i*3 + j];
		if( arr[0][0]==arr[0][1] && arr[0][1]==arr[0][2] && arr[0][0]!=' ' && arr[0][0]!='D')
 		return 0;
 		else if( arr[1][0]==arr[1][1] && arr[1][1]==arr[1][2] && arr[1][0]!=' ' && arr[1][0]!='D')
 		return 0;
 		else if( arr[2][0]==arr[2][1] && arr[2][1]==arr[2][2] && arr[2][0]!=' ' && arr[2][0]!='D')
 		return 0;
 		else if( arr[0][0]==arr[1][0] && arr[1][0]==arr[2][0] && arr[0][0]!=' ' && arr[0][0]!='D')
 		return 0;
 		else if( arr[0][1]==arr[1][1] && arr[1][1]==arr[2][1] && arr[0][1]!=' ' && arr[0][1]!='D')
 		return 0;
 		else if( arr[0][2]==arr[1][2] && arr[1][2]==arr[2][2] && arr[0][2]!=' ' && arr[0][2]!='D')
 		return 0;
 		else if( arr[0][0]==arr[1][1] && arr[1][1]==arr[2][2] && arr[0][0]!=' ' && arr[0][0]!='D')
 		return 0;
 		else if( arr[0][2]==arr[1][1] && arr[1][1]==arr[2][0] && arr[0][2]!=' ' && arr[0][2]!='D')
 		return 0;

 		else if ( (arr[0][0]!=' ') && (arr[0][1]!=' ') && (arr[0][2]!=' ') && (arr[1][0]!=' ') && (arr[1][1]!=' ') && (arr[1][2]!=' ') && (arr[2][0]!=' ') &&(arr[2][1]!=' ') &&(arr[2][2]!=' ') )
 		return 2;
 		
 		return 1;	
	}

	public static void main(String[] args)
	{
		SwingUtilities.invokeLater(new Runnable(){
			public void run()
			{new ultimate_tic_tac_toe();}
		});
	}
}