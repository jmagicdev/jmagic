package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Rush of Battle")
@Types({Type.SORCERY})
@ManaCost("3W")
@ColorIdentity({Color.WHITE})
public final class RushofBattle extends Card
{
	public RushofBattle(GameState state)
	{
		super(state);

		// Creatures you control get +2/+1 until end of turn.
		this.addEffect(ptChangeUntilEndOfTurn(CREATURES_YOU_CONTROL, +2, +1, "Creatures you control get +2/+1 until end of turn."));

		// Warrior creatures you control gain lifelink until end of turn.
		// (Damage dealt by those Warriors also causes their controller to gain
		// that much life.)
		SetGenerator yourWarriors = Intersect.instance(ControlledBy.instance(You.instance()), HasType.instance(Type.CREATURE), HasSubType.instance(SubType.WARRIOR));
		this.addEffect(addAbilityUntilEndOfTurn(yourWarriors, org.rnd.jmagic.abilities.keywords.Lifelink.class, "Warrior creatures you control gain lifelink until end of turn."));
	}
}
