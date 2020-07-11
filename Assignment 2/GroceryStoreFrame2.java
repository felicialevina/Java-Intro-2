import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.io.*;

public class GroceryStoreFrame extends JFrame
{
    private static final int FRAME_WIDTH = 900;
    private static final int FRAME_HEIGHT = 800;
    private JPanel groceryCartPanel = new JPanel(new BorderLayout());
    private JPanel cashRegisterPanel = new JPanel(new BorderLayout());
    private JPanel scanItemsPanel = new JPanel(new BorderLayout());
    private JPanel nutritionScannerPanel = new JPanel(new BorderLayout());
    private JLabel groceryCartLabel = new JLabel("Grocery Cart" );
    private JLabel cashRegisterLabel = new JLabel("Cash Register");
    private JLabel cartNutritionInformationLabel = new JLabel("Cart Nutrition Information");
    private JButton refillButton = new JButton("REFILL");
    private JButton checkoutButton = new JButton("CHECKOUT");
    private JButton pickUpButton = new JButton("PICK UP");
    private JButton addButton = new JButton("ADD");
    private JButton scanButton = new JButton("SCAN");
    private JButton scanFoodItemButton = new JButton("SCAN FOOD ITEM");
    private JButton clearButton = new JButton("CLEAR");
    private JButton calsButton = new JButton("Cals");
    private JButton carbsButton = new JButton("Carbs");
    private JButton fatButton = new JButton("Fat");
    private JButton sugarButton = new JButton("Sugar");
    private JTextArea groceryCartTextArea = new JTextArea();
    private JTextArea cashRegisterTextArea = new JTextArea();
    private JTextArea nutritionInformationTextArea = new JTextArea();
    private JScrollPane groceryCartScroll = new JScrollPane(groceryCartTextArea);
    private JScrollPane cashRegisterScroll = new JScrollPane(cashRegisterTextArea);
    private JScrollPane nutritionScroll = new JScrollPane(nutritionInformationTextArea);
    private GroceryCart groceryList = new GroceryCart();
    private CashRegister registerList = new CashRegister();
    private NutritionScanner scanner = new NutritionScanner();
    private ConveyerBelt conveyer = new ConveyerBelt();
    private Boolean check = true;
    private Boolean intersectCheck = true;

    public GroceryStoreFrame()
    {
	createGroceryCartPanel();
	createCashRegisterPanel();
	createScanItemsPanel();
	createNutritionScannerPanel();

	setSize(FRAME_WIDTH, FRAME_HEIGHT);
    }

    public void createGroceryCartPanel()
    {
	/**
	   Sets the size of the panel
	*/
       	groceryCartPanel.setPreferredSize(new Dimension(250,300));

	/**
	   Creates the Text Area and the Scroll
	*/
	groceryList.fill();
	groceryList.display(groceryCartTextArea);
	groceryCartScroll.setSize(250,300);
	groceryCartPanel.add(groceryCartScroll, BorderLayout.CENTER);

	/**
	   Creates the Grocery Cart label and Refill Button
	*/
	JPanel groceryTitlePanel = new JPanel(new FlowLayout());
	groceryCartPanel.add(groceryTitlePanel, BorderLayout.NORTH);
	groceryTitlePanel.add(groceryCartLabel);
	groceryTitlePanel.add(refillButton);
	ActionListener refillListener = new RefillButtonListener();
	refillButton.addActionListener(refillListener);

	/**
	   Adds the panel to the frame
	*/
	add(groceryCartPanel, BorderLayout.WEST);
    }

    /**
       ActionListener for refill button
       Fill the grocery cart arraylist
       Display the grocery items in the arraylist
     */

    class RefillButtonListener implements ActionListener
    {  
	public void actionPerformed(ActionEvent event)
	{
	    groceryCartTextArea.setText("");
	    groceryList.fill();
	    groceryList.display(groceryCartTextArea);
	}
    }
    
    public void createCashRegisterPanel()
    {
	/**
	   Sets the size of the panel
	*/
	cashRegisterPanel.setPreferredSize(new Dimension(250,300));

	/**
	   Creates the Cash Register Label and Checkout Button
	*/
	JPanel cashRegisterTitlePanel = new JPanel(new FlowLayout());
	cashRegisterPanel.add(cashRegisterTitlePanel, BorderLayout.NORTH);
	cashRegisterTitlePanel.add(cashRegisterLabel);
	cashRegisterTitlePanel.add(checkoutButton);
	ActionListener checkoutListener = new checkoutButtonListener();
	checkoutButton.addActionListener(checkoutListener);

	/**
	   Creates Text Area and the Scroll
	*/
	cashRegisterScroll.setSize(250, 300);
	cashRegisterPanel.add(cashRegisterScroll, BorderLayout.CENTER);

	/**
	   Adds the panel to the frame
	*/
	add(cashRegisterPanel, BorderLayout.EAST);
    }
        
    /**
       ActionListener for checkout button
       Set the text area and array list of cash register to empty
    */
    class checkoutButtonListener implements ActionListener
    {
	public void actionPerformed(ActionEvent event)
	{	
	    cashRegisterTextArea.setText("");
	    registerList.clear();
	}

    }
    
    public void createScanItemsPanel()
    {	
	/**
	   Sets the size of the panel
	*/
	scanItemsPanel.setPreferredSize(new Dimension(400,300));

	/**
	   Creates the Scan Items Button
	*/
	JPanel scanItemsTitlePanel = new JPanel(new FlowLayout());
	scanItemsPanel.add(scanItemsTitlePanel, BorderLayout.NORTH);
	scanItemsTitlePanel.add(pickUpButton);
	ActionListener pickUpListener = new pickUpButtonListener();
	pickUpButton.addActionListener(pickUpListener);
	scanItemsTitlePanel.add(addButton);
	ActionListener addListener = new addButtonListener();
	addButton.addActionListener(addListener);
	scanItemsTitlePanel.add(scanButton);
	ActionListener scanListener = new scanButtonListener();
	scanButton.addActionListener(scanListener);

	scanItemsPanel.add(conveyer);

	/**
	   Adds the panel to the frame
	*/
	add(scanItemsPanel, BorderLayout.CENTER);
    }

    /**
       ActionListener for pick up button
       Takes the next grocery item from the cart
     */
    class pickUpButtonListener implements ActionListener
    {
	public void actionPerformed(ActionEvent event)
	{
	    if(groceryList.getTotalItems() != 0)
		{
		    if(conveyer.checkIntersection() == true)
			{
			    intersectCheck = true;
			}
		    else
			{
			    intersectCheck = false;
			}

		    if(check == true || intersectCheck == true)
			{
			    groceryCartTextArea.setText("");
			    GroceryItem food = groceryList.removeTopItem();
			    groceryList.display(groceryCartTextArea);
			    check = conveyer.setPickedUpItem(food);
			}
		    else
			{
			    groceryCartTextArea.setText("");
			    groceryList.display(groceryCartTextArea);
			}
		}

	    else  
		{
		    groceryCartTextArea.setText("");
		}
	}
    }

    /**
       ActionListener for add button
       Adds the picked up item to the conveyer belt
    */    
    class addButtonListener implements ActionListener
    {
	public void actionPerformed(ActionEvent event)
	{
	    conveyer.addItem();
	    check = true;
	}
    }

    /**
       ActionListener for scan button
       Scans the grocery item on the conveyer belt
    */
    class scanButtonListener implements ActionListener
    {
	public void actionPerformed(ActionEvent event)
	{
	    GroceryItem food = conveyer.removeItem();
	    if(food != null)
		{
		    cashRegisterTextArea.setText("");
		    registerList.scanItem(food);
		    registerList.displayAll(cashRegisterTextArea);
		}
	}
    }
    
    public void createNutritionScannerPanel()
    {
	/**
	   Sets the size of the panel
	*/
	nutritionScannerPanel.setPreferredSize(new Dimension(750,300));

	/**
	   Creates the Nutrition Information Label, Scan Food Item Button and Clear Button
	*/
	JPanel nutritionScannerTitlePanel = new JPanel(new FlowLayout());
	nutritionScannerPanel.add(nutritionScannerTitlePanel, BorderLayout.NORTH);
	nutritionScannerTitlePanel.add(cartNutritionInformationLabel);
	nutritionScannerTitlePanel.add(scanFoodItemButton);
       	ActionListener scanFoodItemListener = new scanFoodItemButtonListener();
	scanFoodItemButton.addActionListener(scanFoodItemListener);
	nutritionScannerTitlePanel.add(clearButton);
       	ActionListener clearListener = new clearButtonListener();
	clearButton.addActionListener(clearListener);
	
	/**
	   Creates the cals Button, Carbs Button, Fat Button and Sugar Button
	*/
	JPanel nutritionScannerBottomPanel = new JPanel(new FlowLayout());
	nutritionScannerPanel.add(nutritionScannerBottomPanel, BorderLayout.SOUTH);
	nutritionScannerBottomPanel.add(calsButton);
       	ActionListener calsListener = new calsButtonListener();
       	calsButton.addActionListener(calsListener);
	nutritionScannerBottomPanel.add(carbsButton);
	ActionListener carbsListener = new carbsButtonListener();
	carbsButton.addActionListener(carbsListener);
	nutritionScannerBottomPanel.add(fatButton);
      	ActionListener fatListener = new fatButtonListener();
        fatButton.addActionListener(fatListener);
	nutritionScannerBottomPanel.add(sugarButton);
      	ActionListener sugarListener = new sugarButtonListener();
	sugarButton.addActionListener(sugarListener);

	/**
	   Creates the Text Area and the Scroll
	*/
        nutritionScroll.setSize(750,300);
	nutritionScannerPanel.add(nutritionScroll, BorderLayout.CENTER);

	/**
	   Adds the panel to the frame
	*/
	add(nutritionScannerPanel, BorderLayout.SOUTH);
    }

    /**
       ActionListener for scan food item button
       Take the next item in grocerylist and scan it based on the foodCode
       Display the result in nutrition information text area
    */
    
    class scanFoodItemButtonListener implements ActionListener
    {
	public void actionPerformed(ActionEvent event)
	{ 
	    if (groceryList.getTotalItems() == 0)
		{
		    groceryList.fill();
		    groceryList.resetNextItem();
		}

	    nutritionInformationTextArea.setText("");

	    GroceryItem next = groceryList.getNextItem();

	    if (next == null)
		{
		    scanner.displayAll(nutritionInformationTextArea);
		}

	    else
		{
		    scanner.scanFoodCode(next.getFoodCode());
		    scanner.displayAll(nutritionInformationTextArea);
		}	    
	}
    }

    /**
       ActionListener clear button
       Clear the nutrition Information text area and reset next item back to the first item in grocery cart arraylist
     */
    class clearButtonListener implements ActionListener
    {
	public void actionPerformed(ActionEvent event)
	{
	    nutritionInformationTextArea.setText("");
      	    scanner.clearList();
      	    groceryList.resetNextItem();
	}
    }
            
    /**
       ActionListener for cals button
       Sorts cals from highest to lowest
    */
    class calsButtonListener implements ActionListener
    {
	public void actionPerformed(ActionEvent event)
	{
      	    nutritionInformationTextArea.setText("");
	    Collections.sort(scanner.getFinalList());
	    scanner.displayAll(nutritionInformationTextArea);
	}
    }
     
    /**
       ActionListener for sugar button
       Sorts sugar from highest to lowest
    */
    class sugarButtonListener implements ActionListener
    {
	public void actionPerformed(ActionEvent event)
	{
	    nutritionInformationTextArea.setText("");
	    Collections.sort(scanner.getFinalList(), new FoodTypeSugarComparator());
	    scanner.displayAll(nutritionInformationTextArea);
	}
    }

    /**
       ActionListener for fat button
       Sorts fat from highest to lowest
    */
    class fatButtonListener implements ActionListener
    {
	public void actionPerformed(ActionEvent event)
	{
	    nutritionInformationTextArea.setText("");
	    Collections.sort(scanner.getFinalList(), new FoodTypeFatComparator());
	    scanner.displayAll(nutritionInformationTextArea);
	}
    }

    /**
       ActionListener for carbs button
       Sorts carbs from highest to lowest
    */
    class carbsButtonListener implements ActionListener
    {
	public void actionPerformed(ActionEvent event)
	{
	    nutritionInformationTextArea.setText("");
	    Collections.sort(scanner.getFinalList(), new FoodTypeCarbsComparator());
	    scanner.displayAll(nutritionInformationTextArea);
	}
    }
}