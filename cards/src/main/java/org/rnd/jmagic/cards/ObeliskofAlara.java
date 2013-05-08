package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Obelisk of Alara")
@Types({Type.ARTIFACT})
@ManaCost("6")
@Printings({@Printings.Printed(ex = Expansion.CONFLUX, r = Rarity.RARE)})
@ColorIdentity({Color.BLUE, Color.WHITE, Color.GREEN, Color.BLACK, Color.RED})
public final class ObeliskofAlara extends Card
{
	public static final class ObeliskofAlaraAbility5 extends ActivatedAbility
	{
		public ObeliskofAlaraAbility5(GameState state)
		{
			super(state, "(1)(W), (T): You gain 5 life.");
			this.setManaCost(new ManaPool("(1)(W)"));
			this.costsTap = true;
			this.addEffect(gainLife(You.instance(), 5, "You gain 5 life."));
		}
	}

	public static final class ObeliskofAlaraAbility1 extends ActivatedAbility
	{
		public ObeliskofAlaraAbility1(GameState state)
		{
			super(state, "(1)(U), (T): Draw a card, then discard a card.");
			this.setManaCost(new ManaPool("(1)(U)"));
			this.costsTap = true;
			this.addEffect(drawCards(You.instance(), 1, "Draw a card,"));
			this.addEffect(discardCards(You.instance(), 1, "then discard a card."));
		}
	}

	public static final class ObeliskofAlaraAbility2 extends ActivatedAbility
	{
		public ObeliskofAlaraAbility2(GameState state)
		{
			super(state, "(1)(B), (T): Target creature gets -2/-2 until end of turn.");
			this.setManaCost(new ManaPool("(1)(B)"));
			this.costsTap = true;
			Target target = this.addTarget(CreaturePermanents.instance(), "target creature");
			this.addEffect(ptChangeUntilEndOfTurn(targetedBy(target), -2, -2, "Target creature gets -2/-2 until end of turn."));
		}
	}

	public static final class ObeliskofAlaraAbility3 extends ActivatedAbility
	{
		public ObeliskofAlaraAbility3(GameState state)
		{
			super(state, "(1)(R), (T): Obelisk of Alara deals 3 damage to target player.");
			this.setManaCost(new ManaPool("(1)(R)"));
			this.costsTap = true;
			Target target = this.addTarget(Players.instance(), "target player");
			this.addEffect(permanentDealDamage(3, targetedBy(target), "Obelisk of Alara deals 3 damage to target player."));
		}
	}

	public static final class ObeliskofAlaraAbility4 extends ActivatedAbility
	{
		public ObeliskofAlaraAbility4(GameState state)
		{
			super(state, "(1)(G), (T): Target creature gets +4/+4 until end of turn.");
			this.setManaCost(new ManaPool("(1)(G)"));
			this.costsTap = true;
			Target target = this.addTarget(CreaturePermanents.instance(), "target creature");
			this.addEffect(ptChangeUntilEndOfTurn(targetedBy(target), +4, +4, "Target creature gets +4/+4 until end of turn."));
		}
	}

	public ObeliskofAlara(GameState state)
	{
		super(state);

		// (1)(W), (T): You gain 5 life.
		this.addAbility(new ObeliskofAlaraAbility5(state));

		// (1)(U), (T): Draw a card, then discard a card.
		this.addAbility(new ObeliskofAlaraAbility1(state));

		// (1)(B), (T): Target creature gets -2/-2 until end of turn.
		this.addAbility(new ObeliskofAlaraAbility2(state));

		// (1)(R), (T): Obelisk of Alara deals 3 damage to target player.
		this.addAbility(new ObeliskofAlaraAbility3(state));

		// (1)(G), (T): Target creature gets +4/+4 until end of turn.
		this.addAbility(new ObeliskofAlaraAbility4(state));
	}
}
