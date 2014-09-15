package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Mindreaver")
@Types({Type.CREATURE})
@SubTypes({SubType.WIZARD, SubType.HUMAN})
@ManaCost("UU")
@ColorIdentity({Color.BLUE})
public final class Mindreaver extends Card
{
	public static final class MindreaverAbility0 extends EventTriggeredAbility
	{
		public MindreaverAbility0(GameState state)
		{
			super(state, "Whenever you cast a spell that targets Mindreaver, exile the top three cards of target player's library.");
			this.addPattern(heroic());

			SetGenerator target = targetedBy(this.addTarget(Players.instance(), "target player"));

			SetGenerator top = TopCards.instance(3, LibraryOf.instance(target));
			this.addEffect(exile(top, "Exile the top three cards of target player's library."));

			this.getLinkManager().addLinkClass(MindreaverAbility1.class);
		}
	}

	public static final class MindreaverAbility1 extends ActivatedAbility
	{
		public MindreaverAbility1(GameState state)
		{
			super(state, "(U)(U), Sacrifice Mindreaver: Counter target spell with the same name as a card exiled with Mindreaver.");
			this.setManaCost(new ManaPool("(U)(U)"));
			this.addCost(sacrificeThis("Mindreaver"));

			SetGenerator sameName = HasName.instance(NameOf.instance(ChosenFor.instance(LinkedTo.instance(Identity.instance(this)))));
			SetGenerator counterable = Intersect.instance(Spells.instance(), sameName);
			SetGenerator target = targetedBy(this.addTarget(counterable, "target spell with the same name as a card exiled with Mindreaver"));
			this.addEffect(counter(target, "Counter target spell with the same name as a card exiled with Mindreaver."));

			this.getLinkManager().addLinkClass(MindreaverAbility0.class);
		}
	}

	public Mindreaver(GameState state)
	{
		super(state);

		this.setPower(2);
		this.setToughness(1);

		// Heroic \u2014 Whenever you cast a spell that targets Mindreaver,
		// exile the top three cards of target player's library.
		this.addAbility(new MindreaverAbility0(state));

		// (U)(U), Sacrifice Mindreaver: Counter target spell with the same name
		// as a card exiled with Mindreaver.
		this.addAbility(new MindreaverAbility1(state));
	}
}
