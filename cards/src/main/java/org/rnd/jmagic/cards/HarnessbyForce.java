package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.expansions.*;

@Name("Harness by Force")
@Types({Type.SORCERY})
@ManaCost("1RR")
@Printings({@Printings.Printed(ex = JourneyIntoNyx.class, r = Rarity.RARE)})
@ColorIdentity({Color.RED})
public final class HarnessbyForce extends Card
{
	public static final class Strive extends org.rnd.jmagic.abilityTemplates.Strive
	{
		public Strive(GameState state)
		{
			super(state, "Harness by Force", "(2)(R)");
		}
	}

	public HarnessbyForce(GameState state)
	{
		super(state);

		// Strive — Harness by Force costs {2}{R} more to cast for each target
		// beyond the first.
		this.addAbility(new Strive(state));

		Target target = this.addTarget(CreaturePermanents.instance(), "any number of target creatures");
		target.setNumber(0, null);

		// Gain control of any number of target creatures until end of turn.
		ContinuousEffect.Part controlPart = new ContinuousEffect.Part(ContinuousEffectType.CHANGE_CONTROL);
		controlPart.parameters.put(ContinuousEffectType.Parameter.OBJECT, targetedBy(target));
		controlPart.parameters.put(ContinuousEffectType.Parameter.PLAYER, You.instance());
		this.addEffect(createFloatingEffect("Gain control of any number of target creatures until end of turn.", controlPart));

		// Untap those creatures.
		this.addEffect(untap(targetedBy(target), "Untap those creatures."));

		// They gain haste until end of turn.
		this.addEffect(addAbilityUntilEndOfTurn(targetedBy(target), org.rnd.jmagic.abilities.keywords.Haste.class, "They gain haste until end of turn."));

	}
}
