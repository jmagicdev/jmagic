package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Mordant Dragon")
@Types({Type.CREATURE})
@SubTypes({SubType.DRAGON})
@ManaCost("3RRR")
@Printings({@Printings.Printed(ex = Expansion.WORLDWAKE, r = Rarity.RARE)})
@ColorIdentity({Color.RED})
public final class MordantDragon extends Card
{
	public static final class MordantDragonAbility1 extends ActivatedAbility
	{
		public MordantDragonAbility1(GameState state)
		{
			super(state, "(1)(R): Mordant Dragon gets +1/+0 until end of turn.");
			this.setManaCost(new ManaPool("(1)(R)"));
			this.addEffect(ptChangeUntilEndOfTurn(ABILITY_SOURCE_OF_THIS, +1, +0, "Mordant Dragon gets +1/+0 until end of turn."));
		}
	}

	public static final class MordantDragonAbility2 extends EventTriggeredAbility
	{
		public MordantDragonAbility2(GameState state)
		{
			super(state, "Whenever Mordant Dragon deals combat damage to a player, you may have it deal that much damage to target creature that player controls.");
			this.addPattern(whenDealsCombatDamageToAPlayer(ABILITY_SOURCE_OF_THIS));

			SetGenerator triggerDamage = TriggerDamage.instance(This.instance());
			SetGenerator thatPlayer = TakerOfDamage.instance(triggerDamage);
			SetGenerator creaturesTheyControl = Intersect.instance(CreaturePermanents.instance(), ControlledBy.instance(thatPlayer));
			SetGenerator target = targetedBy(this.addTarget(creaturesTheyControl, "target creature that player controls"));

			SetGenerator thatMuch = Count.instance(triggerDamage);
			EventFactory damage = permanentDealDamage(thatMuch, target, "Mordant Dragon deals that much damage to target creature that player controls");
			this.addEffect(youMay(damage, "You may have Mordant Dragon deal that much damage to target creature that player controls."));
		}
	}

	public MordantDragon(GameState state)
	{
		super(state);

		this.setPower(5);
		this.setToughness(5);

		// Flying
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Flying(state));

		// (1)(R): Mordant Dragon gets +1/+0 until end of turn.
		this.addAbility(new MordantDragonAbility1(state));

		// Whenever Mordant Dragon deals combat damage to a player, you may have
		// it deal that much damage to target creature that player controls.
		this.addAbility(new MordantDragonAbility2(state));
	}
}
