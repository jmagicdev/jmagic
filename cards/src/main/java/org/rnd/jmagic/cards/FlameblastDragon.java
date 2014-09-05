package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.expansions.*;

@Name("Flameblast Dragon")
@Types({Type.CREATURE})
@SubTypes({SubType.DRAGON})
@ManaCost("4RR")
@Printings({@Printings.Printed(ex = Magic2012.class, r = Rarity.RARE), @Printings.Printed(ex = ShardsOfAlara.class, r = Rarity.RARE)})
@ColorIdentity({Color.RED})
public final class FlameblastDragon extends Card
{
	public static final class FlameblastDragonAbility1 extends EventTriggeredAbility
	{
		public FlameblastDragonAbility1(GameState state)
		{
			super(state, "Whenever Flameblast Dragon attacks, you may pay (X)(R). If you do, Flameblast Dragon deals X damage to target creature or player.");
			this.addPattern(whenThisAttacks());

			EventFactory mayPayX = new EventFactory(EventType.PLAYER_MAY_PAY_X, "You may pay (X)(R)");
			mayPayX.parameters.put(EventType.Parameter.CAUSE, This.instance());
			mayPayX.parameters.put(EventType.Parameter.PLAYER, You.instance());
			mayPayX.parameters.put(EventType.Parameter.MANA, Identity.fromCollection(new ManaPool("R")));

			SetGenerator target = targetedBy(this.addTarget(CREATURES_AND_PLAYERS, "target creature or player"));
			EventFactory damage = permanentDealDamage(EffectResult.instance(mayPayX), target, "Flameblast Dragon deals X damage to target creature or player");

			this.addEffect(ifThen(mayPayX, damage, "You may pay (X)(R). If you do, Flameblast Dragon deals X damage to target creature or player."));
		}
	}

	public FlameblastDragon(GameState state)
	{
		super(state);

		this.setPower(5);
		this.setToughness(5);

		// Flying
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Flying(state));

		// Whenever Flameblast Dragon attacks, you may pay (X)(R). If you do,
		// Flameblast Dragon deals X damage to target creature or player.
		this.addAbility(new FlameblastDragonAbility1(state));
	}
}
