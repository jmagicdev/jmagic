package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.engine.patterns.*;

@Name("Angelic Skirmisher")
@Types({Type.CREATURE})
@SubTypes({SubType.ANGEL})
@ManaCost("4WW")
@Printings({@Printings.Printed(ex = Expansion.GATECRASH, r = Rarity.RARE)})
@ColorIdentity({Color.WHITE})
public final class AngelicSkirmisher extends Card
{
	public static PlayerInterface.ChooseReason REASON = new PlayerInterface.ChooseReason("AngelicSkirmisher", "Choose first strike, vigilance, or lifelink.", true);

	public static final class AngelicSkirmisherAbility1 extends EventTriggeredAbility
	{
		public AngelicSkirmisherAbility1(GameState state)
		{
			super(state, "At the beginning of each combat, choose first strike, vigilance, or lifelink. Creatures you control gain that ability until end of turn.");

			SimpleEventPattern pattern = new SimpleEventPattern(EventType.BEGIN_STEP);
			pattern.put(EventType.Parameter.STEP, BeginningOfCombatStepOf.instance(Players.instance()));
			this.addPattern(pattern);

			Identity abilities = Identity.instance(org.rnd.jmagic.abilities.keywords.FirstStrike.class, org.rnd.jmagic.abilities.keywords.Vigilance.class, org.rnd.jmagic.abilities.keywords.Lifelink.class);
			EventFactory choose = playerChoose(You.instance(), 1, abilities, PlayerInterface.ChoiceType.CLASS, REASON, "Choose first strike, vigilance, or lifelink.");
			this.addEffect(choose);

			java.util.Map<Class<? extends Keyword>, AbilityFactory> map = new java.util.HashMap<Class<? extends Keyword>, AbilityFactory>();
			map.put(org.rnd.jmagic.abilities.keywords.FirstStrike.class, new SimpleAbilityFactory(org.rnd.jmagic.abilities.keywords.FirstStrike.class));
			map.put(org.rnd.jmagic.abilities.keywords.Vigilance.class, new SimpleAbilityFactory(org.rnd.jmagic.abilities.keywords.Vigilance.class));
			map.put(org.rnd.jmagic.abilities.keywords.Lifelink.class, new SimpleAbilityFactory(org.rnd.jmagic.abilities.keywords.Lifelink.class));

			ContinuousEffect.Part part = new ContinuousEffect.Part(ContinuousEffectType.ADD_ABILITY_TO_OBJECT);
			part.parameters.put(ContinuousEffectType.Parameter.OBJECT, CREATURES_YOU_CONTROL);
			part.parameters.put(ContinuousEffectType.Parameter.ABILITY, MapGet.instance(EffectResult.instance(choose), map));

			EventFactory factory = new EventFactory(EventType.CREATE_FLOATING_CONTINUOUS_EFFECT, "Creatures you control gain that ability until end of turn.");
			factory.parameters.put(EventType.Parameter.CAUSE, This.instance());
			factory.parameters.put(EventType.Parameter.EFFECT, Identity.instance(part));
			this.addEffect(factory);
		}
	}

	public AngelicSkirmisher(GameState state)
	{
		super(state);

		this.setPower(4);
		this.setToughness(4);

		// Flying
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Flying(state));

		// At the beginning of each combat, choose first strike, vigilance, or
		// lifelink. Creatures you control gain that ability until end of turn.
		this.addAbility(new AngelicSkirmisherAbility1(state));
	}
}
