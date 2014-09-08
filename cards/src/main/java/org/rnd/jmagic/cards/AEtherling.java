package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.expansions.*;

@Name("\u00C6therling")
@Types({Type.CREATURE})
@SubTypes({SubType.SHAPESHIFTER})
@ManaCost("4UU")
@Printings({@Printings.Printed(ex = DragonsMaze.class, r = Rarity.RARE)})
@ColorIdentity({Color.BLUE})
public final class AEtherling extends Card
{
	public static final class AEtherlingAbility0 extends ActivatedAbility
	{
		public AEtherlingAbility0(GameState state)
		{
			super(state, "(U): Exile \u00C6therling. Return it to the battlefield under its owner's control at the beginning of the next end step.");
			this.setManaCost(new ManaPool("(U)"));

			EventFactory slide = new EventFactory(SLIDE, "Exile \u00C6therling. Return it to the battlefield under its owner's control at the beginning of the next end step.");
			slide.parameters.put(EventType.Parameter.CAUSE, This.instance());
			slide.parameters.put(EventType.Parameter.TARGET, ABILITY_SOURCE_OF_THIS);
			this.addEffect(slide);
		}
	}

	public static final class AEtherlingAbility1 extends ActivatedAbility
	{
		public AEtherlingAbility1(GameState state)
		{
			super(state, "(U): \u00C6therling can't be blocked this turn.");
			this.setManaCost(new ManaPool("(U)"));
			this.addEffect(createFloatingEffect("\u00C6therling can't be blocked this turn.", unblockable(ABILITY_SOURCE_OF_THIS)));
		}
	}

	public static final class AEtherlingAbility2 extends ActivatedAbility
	{
		public AEtherlingAbility2(GameState state)
		{
			super(state, "(1): \u00C6therling gets +1/-1 until end of turn.");
			this.setManaCost(new ManaPool("(1)"));
			this.addEffect(ptChangeUntilEndOfTurn(ABILITY_SOURCE_OF_THIS, +1, -1, "\u00C6therling gets +1/-1 until end of turn."));
		}
	}

	public static final class AEtherlingAbility3 extends ActivatedAbility
	{
		public AEtherlingAbility3(GameState state)
		{
			super(state, "(1): \u00C6therling gets -1/+1 until end of turn.");
			this.setManaCost(new ManaPool("(1)"));
			this.addEffect(ptChangeUntilEndOfTurn(ABILITY_SOURCE_OF_THIS, -1, +1, "\u00C6therling gets -1/+1 until end of turn."));
		}
	}

	public AEtherling(GameState state)
	{
		super(state);

		this.setPower(4);
		this.setToughness(5);

		// (U): Exile \u00C6therling. Return it to the battlefield under its
		// owner's control at the beginning of the next end step.
		this.addAbility(new AEtherlingAbility0(state));

		// (U): \u00C6therling can't be blocked this turn.
		this.addAbility(new AEtherlingAbility1(state));

		// (1): \u00C6therling gets +1/-1 until end of turn.
		this.addAbility(new AEtherlingAbility2(state));

		// (1): \u00C6therling gets -1/+1 until end of turn.
		this.addAbility(new AEtherlingAbility3(state));
	}
}
