package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Nightveil Specter")
@Types({Type.CREATURE})
@SubTypes({SubType.SPECTER})
@ManaCost("(U/B)(U/B)(U/B)")
@Printings({@Printings.Printed(ex = Expansion.GATECRASH, r = Rarity.RARE)})
@ColorIdentity({Color.BLUE, Color.BLACK})
public final class NightveilSpecter extends Card
{
	public static final class NightveilSpecterAbility1 extends EventTriggeredAbility
	{
		public NightveilSpecterAbility1(GameState state)
		{
			super(state, "Whenever Nightveil Specter deals combat damage to a player, that player exiles the top card of his or her library.");
			this.addPattern(whenThisDealsCombatDamageToAPlayer());

			SetGenerator thatPlayer = TakerOfDamage.instance(TriggerDamage.instance(This.instance()));
			EventFactory exile = exile(TopCards.instance(1, LibraryOf.instance(thatPlayer)), "That player exiles the top card of his or her library.");
			exile.setLink(this);
			this.addEffect(exile);

			this.getLinkManager().addLinkClass(NightveilSpecterAbility2.class);
		}
	}

	public static final class NightveilSpecterAbility2 extends StaticAbility
	{
		public NightveilSpecterAbility2(GameState state)
		{
			super(state, "You may play cards exiled with Nightveil Specter.");

			ContinuousEffect.Part part = new ContinuousEffect.Part(ContinuousEffectType.MAY_PLAY_LOCATION);
			part.parameters.put(ContinuousEffectType.Parameter.OBJECT, ChosenFor.instance(LinkedTo.instance(Identity.instance(this))));
			part.parameters.put(ContinuousEffectType.Parameter.PERMISSION, Identity.instance(new PlayPermission(You.instance())));
			this.addEffectPart(part);

			this.getLinkManager().addLinkClass(NightveilSpecterAbility1.class);
		}
	}

	public NightveilSpecter(GameState state)
	{
		super(state);

		this.setPower(2);
		this.setToughness(3);

		// Flying
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Flying(state));

		// Whenever Nightveil Specter deals combat damage to a player, that
		// player exiles the top card of his or her library.
		this.addAbility(new NightveilSpecterAbility1(state));

		// You may play cards exiled with Nightveil Specter.
		this.addAbility(new NightveilSpecterAbility2(state));
	}
}
