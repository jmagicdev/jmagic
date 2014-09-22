package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Butcher of the Horde")
@Types({Type.CREATURE})
@SubTypes({SubType.DEMON})
@ManaCost("1RWB")
@ColorIdentity({Color.RED, Color.WHITE, Color.BLACK})
public final class ButcheroftheHorde extends Card
{
	public static final PlayerInterface.ChooseReason REASON = new PlayerInterface.ChooseReason("ButcherOfTheHorde", "Choose vigilance, lifelink, or haste.", true);

	public static final class ButcheroftheHordeAbility1 extends ActivatedAbility
	{
		public ButcheroftheHordeAbility1(GameState state)
		{
			super(state, "Sacrifice another creature: Butcher of the Horde gains your choice of vigilance, lifelink, or haste until end of turn.");

			SetGenerator anotherCreature = RelativeComplement.instance(HasType.instance(Type.CREATURE), ABILITY_SOURCE_OF_THIS);
			this.addEffect(sacrifice(You.instance(), 1, anotherCreature, "Sacrifice another creature"));

			SetGenerator abilities = Identity.instance(org.rnd.jmagic.abilities.keywords.FirstStrike.class, org.rnd.jmagic.abilities.keywords.Vigilance.class, org.rnd.jmagic.abilities.keywords.Lifelink.class);
			EventFactory choose = playerChoose(You.instance(), 1, abilities, PlayerInterface.ChoiceType.CLASS, REASON, "Choose vigilance, lifelink, or haste.");
			this.addEffect(choose);

			java.util.Map<Class<? extends Keyword>, AbilityFactory> map = new java.util.HashMap<Class<? extends Keyword>, AbilityFactory>();
			map.put(org.rnd.jmagic.abilities.keywords.Vigilance.class, new SimpleAbilityFactory(org.rnd.jmagic.abilities.keywords.Vigilance.class));
			map.put(org.rnd.jmagic.abilities.keywords.Lifelink.class, new SimpleAbilityFactory(org.rnd.jmagic.abilities.keywords.Lifelink.class));
			map.put(org.rnd.jmagic.abilities.keywords.Haste.class, new SimpleAbilityFactory(org.rnd.jmagic.abilities.keywords.Haste.class));

			ContinuousEffect.Part part = new ContinuousEffect.Part(ContinuousEffectType.ADD_ABILITY_TO_OBJECT);
			part.parameters.put(ContinuousEffectType.Parameter.OBJECT, ABILITY_SOURCE_OF_THIS);
			part.parameters.put(ContinuousEffectType.Parameter.ABILITY, MapGet.instance(EffectResult.instance(choose), map));
			this.addEffect(createFloatingEffect("Butcher of the Horde gains your choice of vigilance, lifelink, or haste until end of turn.", part));
		}
	}

	public ButcheroftheHorde(GameState state)
	{
		super(state);

		this.setPower(5);
		this.setToughness(4);

		// Flying
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Flying(state));

		// Sacrifice another creature: Butcher of the Horde gains your choice of
		// vigilance, lifelink, or haste until end of turn.
		this.addAbility(new ButcheroftheHordeAbility1(state));
	}
}
