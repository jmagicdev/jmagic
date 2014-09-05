package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.expansions.*;

@Name("Mosswort Bridge")
@Types({Type.LAND})
@Printings({@Printings.Printed(ex = Lorwyn.class, r = Rarity.RARE)})
@ColorIdentity({Color.GREEN})
public final class MosswortBridge extends Card
{
	public static final class MosswortHideaway extends org.rnd.jmagic.abilities.keywords.Hideaway
	{
		public static final class MosswortExile extends org.rnd.jmagic.abilities.keywords.Hideaway.Exile
		{
			public MosswortExile(GameState state)
			{
				super(state, MosswortBridgeAbility2.class);
			}
		}

		public MosswortHideaway(GameState state)
		{
			super(state);
		}

		@Override
		protected java.util.List<NonStaticAbility> createNonStaticAbilities()
		{
			return java.util.Collections.<NonStaticAbility>singletonList(new MosswortExile(this.state));
		}
	}

	public static final class MosswortBridgeAbility2 extends ActivatedAbility
	{
		public MosswortBridgeAbility2(GameState state)
		{
			super(state, "(G), (T): You may play the exiled card without paying its mana cost if creatures you control have total power 10 or greater.");
			this.setManaCost(new ManaPool("(G)"));
			this.costsTap = true;

			SetGenerator powerOfCreaturesYouControl = Sum.instance(PowerOf.instance(CREATURES_YOU_CONTROL));
			SetGenerator condition = Intersect.instance(powerOfCreaturesYouControl, Between.instance(10, null));

			EventFactory play = new EventFactory(PLAY_WITHOUT_PAYING_MANA_COSTS, "You may play the exiled card without paying its mana cost");
			play.parameters.put(EventType.Parameter.CAUSE, This.instance());
			play.parameters.put(EventType.Parameter.PLAYER, You.instance());
			play.parameters.put(EventType.Parameter.OBJECT, ChosenFor.instance(LinkedTo.instance(This.instance())));

			EventFactory effect = new EventFactory(EventType.IF_CONDITION_THEN_ELSE, "You may play the exiled card without paying its mana cost if creatures you control have total power 10 or greater.");
			effect.parameters.put(EventType.Parameter.IF, condition);
			effect.parameters.put(EventType.Parameter.THEN, Identity.instance(play));
			this.addEffect(effect);

			this.getLinkManager().addLinkClass(MosswortHideaway.MosswortExile.class);
		}
	}

	public MosswortBridge(GameState state)
	{
		super(state);

		// Hideaway (This land enters the battlefield tapped. When it does, look
		// at the top four cards of your library, exile one face down, then put
		// the rest on the bottom of your library.)
		this.addAbility(new MosswortHideaway(state));

		// (T): Add (G) to your mana pool.
		this.addAbility(new org.rnd.jmagic.abilities.TapForMana.Final(state, "(G)"));

		// (G), (T): You may play the exiled card without paying its mana cost
		// if creatures you control have total power 10 or greater.
		this.addAbility(new MosswortBridgeAbility2(state));
	}
}
