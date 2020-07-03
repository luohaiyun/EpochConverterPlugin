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
	private TitlePanel mTitlePanel;
	private JTextField mConvertField;
	private JButton mConvertButton;
	private JTextField mNowField;
	private JTextArea mConvertTextArea;
	private JTextField mYearField;
	private JTextField mMonField;
	private JTextField mDayField;
	private JTextField mHourField;
	private JTextField mMinField;
	private JTextField mSecField;
	private JButton humanDateToTimestampButton;
	private ComboBox mReConvertComboBox;
	private JTextArea mReconvertResult;
	private ComboBox<SelectionMode> mConvertComboBox;

	protected TimeConverterDialogForm(@Nullable Project project)
	{
		super(project);
	}

	@NotNull
	protected final JComponent getComponent()
	{
		return mRootPanel;
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
	protected final ComboBox<SelectionMode> getConvertComboBox()
	{
		return mConvertComboBox;
	}

	@NotNull
	protected final ComboBox<SelectionMode> getReConvertComboBox()
	{
		return mReConvertComboBox;
	}

	@NotNull
	protected final JTextArea getConvertTextArea()
	{
		return mConvertTextArea;
	}


}
