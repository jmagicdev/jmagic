package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.engine.patterns.*;

@Name("Lazav, Dimir Mastermind")
@SuperTypes({SuperType.LEGENDARY})
@Types({Type.CREATURE})
@SubTypes({SubType.SHAPESHIFTER})
@ManaCost("UUBB")
@Printings({@Printings.Printed(ex = Expansion.GATECRASH, r = Rarity.MYTHIC)})
@ColorIdentity({Color.BLUE, Color.BLACK})
public final class LazavDimirMastermind extends Card
{
	public static final class LazavDimirMastermindAbility1 extends EventTriggeredAbility
	{
		public LazavDimirMastermindAbility1(GameState state)
		{
			super(state, "Whenever a creature card is put into an opponent's graveyard from anywhere, you may have Lazav, Dimir Mastermind become a copy of that card except its name is still Lazav, Dimir Mastermind, it's legendary in addition to its other types, and it gains hexproof and this ability.");

			this.addPattern(new SimpleZoneChangePattern(null, GraveyardOf.instance(OpponentsOf.instance(You.instance())), HasType.instance(Type.CREATURE), false));

			SetGenerator thatCard = NewObjectOf.instance(TriggerZoneChange.instance(This.instance()));

			ContinuousEffect.Part part = new ContinuousEffect.Part(ContinuousEffectType.COPY_OBJECT);
			part.parameters.put(ContinuousEffectType.Parameter.ABILITY, Identity.instance(new SimpleAbilityFactory(LazavDimirMastermindAbility1.class), new SimpleAbilityFactory(org.rnd.jmagic.abilities.keywords.Hexproof.class)));
			part.parameters.put(ContinuousEffectType.Parameter.OBJECT, ABILITY_SOURCE_OF_THIS);
			part.parameters.put(ContinuousEffectType.Parameter.ORIGINAL, thatCard);
			part.parameters.put(ContinuousEffectType.Parameter.RETAIN, Identity.instance(Characteristics.Characteristic.NAME));
			part.parameters.put(ContinuousEffectType.Parameter.TYPE, Identity.instance(SuperType.LEGENDARY));

			EventFactory copy = createFloatingEffect(Empty.instance(), "Have Lazav, Dimir Mastermind become a copy of that card except its name is still Lazav, Dimir Mastermind, it's legendary in addition to its other types, and it gains hexproof and this ability.", part);
			this.addEffect(youMay(copy, "Have Lazav, Dimir Mastermind become a copy of that card except its name is still Lazav, Dimir Mastermind, it's legendary in addition to its other types, and it gains hexproof and this ability."));
		}
	}

	public LazavDimirMastermind(GameState state)
	{
		super(state);

		this.setPower(3);
		this.setToughness(3);

		// Hexproof
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Hexproof(state));

		// Whenever a creature card is put into an opponent's graveyard from
		// anywhere, you may have Lazav, Dimir Mastermind become a copy of that
		// card except its name is still Lazav, Dimir Mastermind, it's legendary
		// in addition to its other types, and it gains hexproof and this
		// ability.
		this.addAbility(new LazavDimirMastermindAbility1(state));
	}
}
