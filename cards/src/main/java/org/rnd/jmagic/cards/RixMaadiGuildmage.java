package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Rix Maadi Guildmage")
@Types({Type.CREATURE})
@SubTypes({SubType.SHAMAN, SubType.HUMAN})
@ManaCost("BR")
@Printings({@Printings.Printed(ex = Expansion.RETURN_TO_RAVNICA, r = Rarity.UNCOMMON)})
@ColorIdentity({Color.BLACK, Color.RED})
public final class RixMaadiGuildmage extends Card
{
	public static final class RixMaadiGuildmageAbility0 extends ActivatedAbility
	{
		public RixMaadiGuildmageAbility0(GameState state)
		{
			super(state, "(B)(R): Target blocking creature gets -1/-1 until end of turn.");
			this.setManaCost(new ManaPool("(B)(R)"));

			Target target = this.addTarget(Intersect.instance(Blocking.instance(), CreaturePermanents.instance()), "target blocking creature");

			this.addEffect(ptChangeUntilEndOfTurn(targetedBy(target), -1, -1, "Target blocking creature gets -1/-1 until end of turn."));
		}
	}

	public static final class RixMaadiGuildmageAbility1 extends ActivatedAbility
	{
		public RixMaadiGuildmageAbility1(GameState state)
		{
			super(state, "(B)(R): Target player who lost life this turn loses 1 life.");
			this.setManaCost(new ManaPool("(B)(R)"));

			state.ensureTracker(new LifeLostThisTurn.LifeTracker());

			SetGenerator target = targetedBy(this.addTarget(LifeLostThisTurn.PlayersWhoLostLife.instance(), "target player who lost life this turn"));
			this.addEffect(loseLife(target, 1, "Target player who lost life this turn loses 1 life."));
		}
	}

	public RixMaadiGuildmage(GameState state)
	{
		super(state);

		this.setPower(2);
		this.setToughness(2);

		// (B)(R): Target blocking creature gets -1/-1 until end of turn.
		this.addAbility(new RixMaadiGuildmageAbility0(state));

		// (B)(R): Target player who lost life this turn loses 1 life.
		this.addAbility(new RixMaadiGuildmageAbility1(state));
	}
}
