package com.github.luohaiyun.plugin.timeconverter.ui.form;

import com.github.luohaiyun.plugin.timeconverter.util.SelectionMode;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.ComboBox;
import com.intellij.openapi.ui.DialogWrapper;
import com.intellij.ui.TitlePanel;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;

public abstract class TimeConverterDialogForm extends DialogWrapper
{
	private JPanel mRootPanel;
	private JTextField mConvertField;
	private JButton mConvertButton;
	private JTextField mNowField;
	private JTextField mYearField;
	private JTextField mMonField;
	private JTextField mDayField;
	private JTextField mHourField;
	private JTextField mMinField;
	private JTextField mSecField;
	private JButton mReConvertButton;
	private ComboBox<SelectionMode> mReConvertComboBox;
	private JTextArea mReConvertTextArea;
	private TitlePanel mTitlePanel;
	private JTextField mConvertGMTTextField;
	private JTextField mConvertLocalTextField;
	private JTextField mConvertRelativeTextField;
	private JLabel mConvertAssumingLabel;
	private JPanel mConvertResultPanel;

	protected TimeConverterDialogForm(@Nullable Project project)
	{
		super(project);
	}

	@NotNull
	protected final JTextField getConvertLocalTextField()
	{
		return mConvertLocalTextField;
	}

	@NotNull
	protected final JTextField getConvertGMTTextField()
	{
		return mConvertGMTTextField;
	}


	@NotNull
	protected final JTextField getConvertRelativeTextField()
	{
		return mConvertRelativeTextField;
	}

	@NotNull
	protected final JLabel getConvertAssumingLabel()
	{
		return mConvertAssumingLabel;
	}

	@NotNull
	protected final JPanel getConvertResultPanel()
	{
		return mConvertResultPanel;
	}

	@NotNull
	protected final JComponent getComponent()
	{
		return mRootPanel;
	}

	@NotNull
	protected final JTextField getNowField()
	{
		return mNowField;
	}

	@NotNull
	protected final JTextField getYearField()
	{
		return mYearField;
	}

	@NotNull
	protected final JTextField getMonField()
	{
		return mMonField;
	}

	@NotNull
	protected final JTextField getDayField()
	{
		return mDayField;
	}

	@NotNull
	protected final JTextField getSecField()
	{
		return mSecField;
	}

	@NotNull
	protected final JTextField getMinField()
	{
		return mMinField;
	}

	@NotNull
	protected final JTextField getHourField()
	{
		return mHourField;
	}

	@NotNull
	protected final JButton getReConvertButton()
	{
		return mReConvertButton;
	}

	@NotNull
	protected final TitlePanel getTitlePanel()
	{
		return mTitlePanel;
	}

	@NotNull
	protected final JTextField getConvertField()
	{
		return mConvertField;
	}

	@NotNull
	protected final JButton getConvertButton()
	{
		return mConvertButton;
	}

	@NotNull
	protected final ComboBox<SelectionMode> getReConvertComboBox()
	{
		return mReConvertComboBox;
	}

	@NotNull
	protected final JTextArea getReConvertTextArea()
	{
		return mReConvertTextArea;
	}

}
