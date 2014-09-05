package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.expansions.*;
import org.rnd.jmagic.engine.patterns.*;

@Name("Rhox Faithmender")
@Types({Type.CREATURE})
@SubTypes({SubType.MONK, SubType.RHINO})
@ManaCost("3W")
@Printings({@Printings.Printed(ex = Magic2013.class, r = Rarity.RARE)})
@ColorIdentity({Color.WHITE})
public final class RhoxFaithmender extends Card
{
	public static final class RhoxFaithmenderAbility1 extends StaticAbility
	{
		public RhoxFaithmenderAbility1(GameState state)
		{
			super(state, "If you would gain life, you gain twice that much life instead.");

			SimpleEventPattern toReplace = new SimpleEventPattern(EventType.GAIN_LIFE_ONE_PLAYER);
			toReplace.put(EventType.Parameter.PLAYER, You.instance());
			EventReplacementEffect replacement = new EventReplacementEffect(state.game, "If you would gain life, you gain twice that much life instead.", toReplace);

			EventFactory factory = new EventFactory(EventType.GAIN_LIFE_ONE_PLAYER, "Gain twice that much life instead.");
			SetGenerator originalNumber = EventParameter.instance(replacement.replacedByThis(), EventType.Parameter.NUMBER);
			factory.parameters.put(EventType.Parameter.NUMBER, Multiply.instance(originalNumber, numberGenerator(2)));
			replacement.addEffect(factory);
			this.addEffectPart(replacementEffectPart(replacement));
		}
	}

	public RhoxFaithmender(GameState state)
	{
		super(state);

		this.setPower(1);
		this.setToughness(5);

		// Lifelink (Damage dealt by this creature also causes you to gain that
		// much life.)
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Lifelink(state));

		// If you would gain life, you gain twice that much life instead.
		this.addAbility(new RhoxFaithmenderAbility1(state));
	}
}
