package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.expansions.*;
import org.rnd.jmagic.engine.patterns.*;

@Name("Tatsumasa, the Dragon's Fang")
@SuperTypes({SuperType.LEGENDARY})
@Types({Type.ARTIFACT})
@SubTypes({SubType.EQUIPMENT})
@ManaCost("6")
@Printings({@Printings.Printed(ex = ChampionsOfKamigawa.class, r = Rarity.RARE)})
@ColorIdentity({})
public final class TatsumasatheDragonsFang extends Card
{
	public static final class TatsumasatheDragonsFangAbility1 extends ActivatedAbility
	{
		public TatsumasatheDragonsFangAbility1(GameState state)
		{
			super(state, "(6), Exile Tatsumasa, the Dragon's Fang: Put a 5/5 blue Dragon Spirit creature token with flying onto the battlefield. Return Tatsumasa to the battlefield under its owner's control when that token is put into a graveyard.");
			this.setManaCost(new ManaPool("(6)"));
			EventFactory theCost = exile(ABILITY_SOURCE_OF_THIS, "Exile Tatsumasa, the Dragon's Fang");
			this.addCost(theCost);

			SetGenerator exiledTatsumasa = NewObjectOf.instance(CostResult.instance(theCost));

			CreateTokensFactory token = new CreateTokensFactory(1, 5, 5, "Put a 5/5 blue Dragon Spirit creature token with flying onto the battlefield.");
			token.setColors(Color.BLUE);
			token.setSubTypes(SubType.DRAGON, SubType.SPIRIT);
			token.addAbility(org.rnd.jmagic.abilities.keywords.Flying.class);
			EventFactory factory = token.getEventFactory();
			this.addEffect(factory);

			EventFactory returnTatsumasa = new EventFactory(EventType.PUT_ONTO_BATTLEFIELD_UNDER_OWNER_CONTROL, "Return Tatsumasa to the battlefield under its owner's control");
			returnTatsumasa.parameters.put(EventType.Parameter.CAUSE, This.instance());
			returnTatsumasa.parameters.put(EventType.Parameter.OBJECT, exiledTatsumasa);

			ZoneChangePattern dragonDies = new SimpleZoneChangePattern(null, GraveyardOf.instance(Players.instance()), EffectResult.instance(factory), true);

			EventFactory delayedTrigger = new EventFactory(EventType.CREATE_DELAYED_TRIGGER, "Return Tatsumasa to the battlefield under its owner's control when that token is put into a graveyard.");
			delayedTrigger.parameters.put(EventType.Parameter.CAUSE, This.instance());
			delayedTrigger.parameters.put(EventType.Parameter.EFFECT, Identity.instance(returnTatsumasa));
			delayedTrigger.parameters.put(EventType.Parameter.ZONE_CHANGE, Identity.instance(dragonDies));

			this.addEffect(delayedTrigger);
		}
	}

	public TatsumasatheDragonsFang(GameState state)
	{
		super(state);

		// Equipped creature gets +5/+5.
		this.addAbility(new org.rnd.jmagic.abilities.StaticPTChange(state, EquippedBy.instance(This.instance()), "Equipped creature", +5, +5, false));

		// (6), Exile Tatsumasa, the Dragon's Fang: Put a 5/5 blue Dragon Spirit
		// creature token with flying onto the battlefield. Return Tatsumasa to
		// the battlefield under its owner's control when that token is put into
		// a graveyard.
		this.addAbility(new TatsumasatheDragonsFangAbility1(state));

		// Equip (3)
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Equip(state, "(3)"));
	}
}
