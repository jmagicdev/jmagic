package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Soul of Shandalar")
@Types({Type.CREATURE})
@SubTypes({SubType.AVATAR})
@ManaCost("4RR")
@ColorIdentity({Color.RED})
public final class SoulofShandalar extends Card
{
	public static final class SoulofShandalarAbility1 extends ActivatedAbility
	{
		public SoulofShandalarAbility1(GameState state)
		{
			super(state, "(3)(R)(R): Soul of Shandalar deals 3 damage to target player and 3 damage to up to one target creature that player controls.");
			this.setManaCost(new ManaPool("(3)(R)(R)"));

			SetGenerator player = targetedBy(this.addTarget(Players.instance(), "target player"));

			SetGenerator playersCreatures = Intersect.instance(ControlledBy.instance(player), HasType.instance(Type.CREATURE));
			Target targetCreature = this.addTarget(playersCreatures, "up to one target creature that player controls");
			targetCreature.setNumber(0, 1);
			SetGenerator creature = targetedBy(targetCreature);

			this.addEffect(permanentDealDamage(3, Union.instance(player, creature), "Soul of Shandalar deals 3 damage to target player and 3 damage to up to one target creature that player controls."));
		}
	}

	public static final class SoulofShandalarAbility2 extends ActivatedAbility
	{
		public SoulofShandalarAbility2(GameState state)
		{
			super(state, "(3)(R)(R), Exile Soul of Shandalar from your graveyard: Soul of Shandalar deals 3 damage to target player and 3 damage to up to one target creature that player controls.");
			this.setManaCost(new ManaPool("(3)(R)(R)"));
			this.addCost(exile(ABILITY_SOURCE_OF_THIS, "Exile Soul of Shandalar from your graveyard"));
			this.activateOnlyFromGraveyard();

			SetGenerator player = targetedBy(this.addTarget(Players.instance(), "target player"));

			SetGenerator playersCreatures = Intersect.instance(ControlledBy.instance(player), HasType.instance(Type.CREATURE));
			Target targetCreature = this.addTarget(playersCreatures, "up to one target creature that player controls");
			targetCreature.setNumber(0, 1);
			SetGenerator creature = targetedBy(targetCreature);

			this.addEffect(permanentDealDamage(3, Union.instance(player, creature), "Soul of Shandalar deals 3 damage to target player and 3 damage to up to one target creature that player controls."));

		}
	}

	public SoulofShandalar(GameState state)
	{
		super(state);

		this.setPower(6);
		this.setToughness(6);

		// First strike
		this.addAbility(new org.rnd.jmagic.abilities.keywords.FirstStrike(state));

		// (3)(R)(R): Soul of Shandalar deals 3 damage to target player and 3
		// damage to up to one target creature that player controls.
		this.addAbility(new SoulofShandalarAbility1(state));

		// (3)(R)(R), Exile Soul of Shandalar from your graveyard: Soul of
		// Shandalar deals 3 damage to target player and 3 damage to up to one
		// target creature that player controls.
		this.addAbility(new SoulofShandalarAbility2(state));
	}
}
