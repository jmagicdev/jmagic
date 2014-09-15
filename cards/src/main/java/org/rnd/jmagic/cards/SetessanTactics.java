package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.abilities.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Setessan Tactics")
@Types({Type.INSTANT})
@ManaCost("1G")
@ColorIdentity({Color.GREEN})
public final class SetessanTactics extends Card
{
	public static class CanFight extends ActivatedAbility
	{
		public CanFight(GameState state)
		{
			super(state, "(T): This creature fights another target creature.");
			this.costsTap = true;

			SetGenerator anotherCreature = RelativeComplement.instance(CreaturePermanents.instance(), ABILITY_SOURCE_OF_THIS);
			SetGenerator target = targetedBy(this.addTarget(anotherCreature, "another target creature"));
			this.addEffect(fight(Union.instance(ABILITY_SOURCE_OF_THIS, target), "This creature fights another target creature."));
		}
	}

	public SetessanTactics(GameState state)
	{
		super(state);

		// Strive — Setessan Tactics costs (G) more to cast for each target
		// beyond the first.
		this.addAbility(new Strive(state, "Setessan Tactics", "(G)"));

		// Until end of turn, any number of target creatures each get +1/+1 and
		// gain "(T): This creature fights another target creature."
		Target t = this.addTarget(CreaturePermanents.instance(), "any number of target creatures");
		t.setNumber(0, null);
		this.addEffect(ptChangeAndAbilityUntilEndOfTurn(targetedBy(t), +1, +1, "Until end of turn, any number of target creatures each get +1/+1 and gain \"(T): This creature fights another target creature.\"", CanFight.class));
	}
}
