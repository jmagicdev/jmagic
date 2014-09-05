package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.expansions.*;

@Name("Codex Shredder")
@Types({Type.ARTIFACT})
@ManaCost("1")
@Printings({@Printings.Printed(ex = ReturnToRavnica.class, r = Rarity.UNCOMMON)})
@ColorIdentity({})
public final class CodexShredder extends Card
{
	public static final class CodexShredderAbility0 extends ActivatedAbility
	{
		public CodexShredderAbility0(GameState state)
		{
			super(state, "(T): Target player puts the top card of his or her library into his or her graveyard.");
			this.costsTap = true;

			SetGenerator target = targetedBy(this.addTarget(Players.instance(), "target player"));
			this.addEffect(millCards(target, 1, "Target player puts the top card of his or her library into his or her graveyard."));
		}
	}

	public static final class CodexShredderAbility1 extends ActivatedAbility
	{
		public CodexShredderAbility1(GameState state)
		{
			super(state, "(5), (T), Sacrifice Codex Shredder: Return target card from your graveyard to your hand.");
			this.setManaCost(new ManaPool("(5)"));
			this.costsTap = true;
			this.addCost(sacrificeThis("Codex Shredder"));

			SetGenerator target = targetedBy(this.addTarget(InZone.instance(GraveyardOf.instance(You.instance())), "target card in your graveyard"));
			this.addEffect(bounce(target, "Return target card from your graveyard to your hand."));
		}
	}

	public CodexShredder(GameState state)
	{
		super(state);

		// (T): Target player puts the top card of his or her library into his
		// or her graveyard.
		this.addAbility(new CodexShredderAbility0(state));

		// (5), (T), Sacrifice Codex Shredder: Return target card from your
		// graveyard to your hand.
		this.addAbility(new CodexShredderAbility1(state));
	}
}
