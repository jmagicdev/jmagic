package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Sutured Ghoul")
@Types({Type.CREATURE})
@SubTypes({SubType.ZOMBIE})
@ManaCost("4BBB")
@Printings({@Printings.Printed(ex = Expansion.MAGIC_2012, r = Rarity.RARE), @Printings.Printed(ex = Expansion.JUDGMENT, r = Rarity.RARE)})
@ColorIdentity({Color.BLACK})
public final class SuturedGhoul extends Card
{
	public static final class SuturedGhoulAbility1 extends StaticAbility
	{
		public SuturedGhoulAbility1(GameState state)
		{
			super(state, "As Sutured Ghoul enters the battlefield, exile any number of creature cards from your graveyard.");
			this.canApply = NonEmpty.instance();

			ZoneChangeReplacementEffect replacement = new ZoneChangeReplacementEffect(this.game, "As Sutured Ghoul enters the battlefield, exile any number of creature cards from your graveyard.");
			replacement.addPattern(asThisEntersTheBattlefield());

			SetGenerator dead = InZone.instance(GraveyardOf.instance(You.instance()));
			SetGenerator deadCritters = Intersect.instance(dead, HasType.instance(Type.CREATURE));

			EventFactory exile = new EventFactory(EventType.EXILE_CHOICE, "Exile any number of creature cards from your graveyard.");
			exile.parameters.put(EventType.Parameter.CAUSE, This.instance());
			exile.parameters.put(EventType.Parameter.NUMBER, Between.instance(0, null));
			exile.parameters.put(EventType.Parameter.OBJECT, deadCritters);
			exile.parameters.put(EventType.Parameter.PLAYER, You.instance());
			exile.setLink(this);
			replacement.addEffect(exile);

			this.addEffectPart(replacementEffectPart(replacement));

			this.getLinkManager().addLinkClass(SuturedGhoulAbility2.class);
		}
	}

	public static final class SuturedGhoulAbility2 extends CharacteristicDefiningAbility
	{
		public SuturedGhoulAbility2(GameState state)
		{
			super(state, "Sutured Ghoul's power is equal to the total power of the exiled cards and its toughness is equal to their total toughness.", Characteristics.Characteristic.POWER, Characteristics.Characteristic.TOUGHNESS);
			this.getLinkManager().addLinkClass(SuturedGhoulAbility1.class);

			SetGenerator exiledCards = ChosenFor.instance(LinkedTo.instance(Identity.instance(this)));
			SetGenerator power = Sum.instance(PowerOf.instance(exiledCards));
			SetGenerator toughness = Sum.instance(ToughnessOf.instance(exiledCards));
			this.addEffectPart(setPowerAndToughness(This.instance(), power, toughness));
		}
	}

	public SuturedGhoul(GameState state)
	{
		super(state);

		this.setPower(0);
		this.setToughness(0);

		// Trample
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Trample(state));

		// As Sutured Ghoul enters the battlefield, exile any number of creature
		// cards from your graveyard.
		this.addAbility(new SuturedGhoulAbility1(state));

		// Sutured Ghoul's power is equal to the total power of the exiled cards
		// and its toughness is equal to their total toughness.
		this.addAbility(new SuturedGhoulAbility2(state));
	}
}
