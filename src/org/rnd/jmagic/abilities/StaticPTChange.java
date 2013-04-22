package org.rnd.jmagic.abilities;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;

// TODO : get rid of this class. with the addAbility and modifyPT
// convenience methods it's really not needed, and there's a lot of complex
// code here that could be eliminated.
public final class StaticPTChange extends StaticAbility
{
	private SetGenerator who;
	private String whoText;
	private int power;
	private int toughness;
	private boolean plural;
	private Class<?> ability;

	/**
	 * @param power The change to power.
	 * @param toughness The change to toughness.
	 * @return a string representing a power and toughness change.
	 */
	public static String ptString(int power, int toughness)
	{
		if(power == 0 && toughness == 0)
			return "+0/+0";

		String powerString;
		if(power == 0)
			powerString = toughness > 0 ? "+0" : "-0";
		else
			powerString = (power > 0 ? "+" : "") + power;

		String toughnessString;
		if(toughness == 0)
			toughnessString = power > 0 ? "+0" : "-0";
		else
			toughnessString = (toughness > 0 ? "+" : "") + toughness;

		return powerString + "/" + toughnessString;
	}

	public StaticPTChange(GameState state, SetGenerator who, String whoText, int power, int toughness, boolean plural)
	{
		super(state, whoText + (plural ? " get " : " gets ") + ptString(power, toughness) + ".");
		this.who = who;
		this.whoText = whoText;
		this.power = power;
		this.toughness = toughness;
		this.plural = plural;
		this.ability = null;

		this.addEffectPart(modifyPowerAndToughness(who, power, toughness));
	}

	public StaticPTChange(GameState state, SetGenerator who, String whoText, int power, int toughness, Class<?> ability, boolean plural)
	{
		super(state, whoText + (plural ? " get " : " gets ") + ptString(power, toughness) + (plural ? " and have " : " and has ") + ability.getAnnotation(Name.class).value().toLowerCase() + ".");
		this.who = who;
		this.whoText = whoText;
		this.power = power;
		this.toughness = toughness;
		this.ability = ability;
		this.plural = plural;

		this.addEffectPart(modifyPowerAndToughness(who, power, toughness));

		this.addEffectPart(addAbilityToObject(who, ability));
	}

	@Override
	public StaticPTChange create(Game game)
	{
		if(this.ability == null)
			return new StaticPTChange(game.physicalState, this.who, this.whoText, this.power, this.toughness, this.plural);

		return new StaticPTChange(game.physicalState, this.who, this.whoText, this.power, this.toughness, this.ability, this.plural);
	}
}
