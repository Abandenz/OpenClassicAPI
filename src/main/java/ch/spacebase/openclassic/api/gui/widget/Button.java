package ch.spacebase.openclassic.api.gui.widget;

import ch.spacebase.openclassic.api.Color;
import ch.spacebase.openclassic.api.OpenClassic;
import ch.spacebase.openclassic.api.asset.texture.SubTexture;
import ch.spacebase.openclassic.api.gui.GuiScreen;
import ch.spacebase.openclassic.api.gui.Screen;
import ch.spacebase.openclassic.api.render.RenderHelper;
import ch.spacebase.openclassic.api.util.GuiTextures;

/**
 * Represents a button.
 */
public class Button extends Widget {
	
	private String text;
	private boolean active = true;

	public Button(int id, int x, int y, Screen parent, String text) {
		this(id, x, y, 400, 40, parent, text);
	}
	
	public Button(int id, int x, int y, int width, int height, Screen parent, String text) {
		super(id, x, y, width, height, parent);
		this.text = text;
	}
	
	/**
	 * Gets the button's text.
	 * @return The button's text.
	 */
	public String getText() {
		return this.text;
	}
	
	/**
	 * Sets the button's text.
	 * @param text Text to set.
	 */
	public void setText(String text) {
		this.text = text;
		if(this.text == null) this.text = "";
	}
	
	/**
	 * Returns true if this button is active.
	 * @return True if the button is active.
	 */
	public boolean isActive() {
		return this.active;
	}
	
	/**
	 * Sets whether the button is active.
	 * @param active Whether the button is active.
	 */
	public void setActive(boolean active) {
		this.active = active;
	}
	
	@Override
	public void onMouseClick(int x, int y, int button) {
		if(button != 0 || !this.isActive()) return;
		
		OpenClassic.getClient().getAudioManager().playSound("generic.click", 1, 1);
		for(Widget widget : this.parent.getWidgets()) {
			if(widget instanceof ButtonList && ((ButtonList) widget).getButtons().contains(this)) {
				((ButtonList) widget).onButtonClick(this);
				return;
			}
		}
		
		if(this.parent instanceof GuiScreen) {
			((GuiScreen) this.parent).onButtonClick(this);
		}
	}

	@Override
	public void render() {
		int mouseX = RenderHelper.getHelper().getRenderMouseX();
		int mouseY = RenderHelper.getHelper().getRenderMouseY();
		
		SubTexture texture = GuiTextures.BUTTON;
		boolean hover = mouseX >= this.x && mouseY >= this.y && mouseX < this.x + this.width && mouseY < this.y + this.height;
		if (!this.active) {
			texture = GuiTextures.BUTTON_INACTIVE;
		} else if (hover) {
			texture = GuiTextures.BUTTON_HOVER;
		}

		SubTexture part1 = texture.getParent().getSubTexture(texture.getX1(), texture.getY1(), this.width / 2, this.height / 2);
		SubTexture part2 = texture.getParent().getSubTexture(texture.getX1() + 400 - this.width / 2, texture.getY1(), this.width / 2, this.height / 2);
		SubTexture part3 = texture.getParent().getSubTexture(texture.getX1(), texture.getY1() + 40 - this.height / 2, this.width / 2, this.height / 2);
		SubTexture part4 = texture.getParent().getSubTexture(texture.getX1() + 400 - this.width / 2, texture.getY1() + 40 - this.height / 2, this.width / 2, this.height / 2);
		RenderHelper.getHelper().drawSubTex(part1, this.x, this.y, 1);
		RenderHelper.getHelper().drawSubTex(part2, this.x + this.width / 2, this.y, 1);
		RenderHelper.getHelper().drawSubTex(part3, this.x, this.y + this.height / 2, 1);
		RenderHelper.getHelper().drawSubTex(part4, this.x + this.width / 2, this.y + this.height / 2, 1);

		String message = this.text;
		if(message.length() > 30) {
			message = message.substring(0, 30) + "...";
		}
		
		if (!this.active) {
			RenderHelper.getHelper().renderText(Color.GRAY + message, this.x + (this.width - 8) / 2, this.y + (this.height - 24) / 2);
		} else if (hover) {
			RenderHelper.getHelper().renderText(Color.YELLOW + message, this.x + (this.width - 8) / 2, this.y + (this.height - 24) / 2);
		} else {
			RenderHelper.getHelper().renderText(message, this.x + (this.width - 8) / 2, this.y + (this.height - 24) / 2);
		}
	}
}
